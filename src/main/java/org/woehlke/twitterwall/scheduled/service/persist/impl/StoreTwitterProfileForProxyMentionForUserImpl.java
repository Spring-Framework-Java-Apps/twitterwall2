package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.ApiException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.service.*;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreTwitterProfileForProxyMentionForUser;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 14.07.17.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreTwitterProfileForProxyMentionForUserImpl implements StoreTwitterProfileForProxyMentionForUser {


    @Override
    public User storeTwitterProfileForProxyMentionForUser(Mention mention, Task task) {
        String msg = "storeTwitterProfileForProxyMentionForUser: "+mention.getUniqueId()+" : "+task.getUniqueId() +" : ";
        try {
            CountedEntities countedEntities = countedEntitiesService.countAll();
            String screenName = mention.getScreenName();
            User foundUser = null;
            User myFoundUser = userService.findByScreenName(screenName);
            if (myFoundUser != null) {
                foundUser = myFoundUser;
            } else {
                TwitterProfile twitterProfile = null;
                try {
                    twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
                } catch (ApiException twitterApiException) {
                    taskService.error(task, twitterApiException, msg, countedEntities);
                    log.error(msg + twitterApiException.getMessage());
                }
                if (twitterProfile != null) {
                    User myFoundUser2 = userTransformService.transform(twitterProfile, task);
                    myFoundUser2 = this.storeUserProcess(myFoundUser2, task);
                    foundUser = myFoundUser2;
                }
            }
            return foundUser;
        } catch (Exception e){
            log.error(msg+e.getMessage());
        }
        return null;
    }

    /**
     * @see StoreEntitiesProcessImpl
     * @see StoreUserProcessImpl
     *
     * @param user User
     * @param task Task
     * @return User
     */
    private User storeUserProcess(User user, Task task){
        String msg = "User.storeUserProcess ";

        try {

            /** @see StoreEntitiesProcessImpl.storeEntitiesProcess(Entities entities, Task task) */
            Set<Url> urls = new LinkedHashSet<>();
            Set<HashTag> hashTags = new LinkedHashSet<>();
            Set<Mention> mentions = new LinkedHashSet<>();
            Set<Media> media = new LinkedHashSet<>();
            Set<TickerSymbol> tickerSymbols = new LinkedHashSet<>();
            for (Url myUrl : user.getEntities().getUrls()) {
                if ((myUrl != null) && (myUrl.isValid())) {
                    Url urlPers = urlService.store(myUrl, task);
                    urls.add(urlPers);
                } else if ((myUrl != null) && (myUrl.isRawUrlsFromDescription())) {
                    String urlStr = myUrl.getUrl();
                    Url urlObj = createPersistentUrl.createPersistentUrlFor(urlStr, task);
                    if ((urlObj != null) && (urlObj.isValid())) {
                        urls.add(urlObj);
                    }
                }
            }
            for (HashTag hashTag : user.getEntities().getHashTags()) {
                if (hashTag.isValid()) {
                    HashTag hashTagPers = hashTagService.store(hashTag, task);
                    hashTags.add(hashTagPers);
                }
            }
            /** hier wird kein Proxy user mehr angelegt */
            for (Mention mention : user.getEntities().getMentions()) {
                if (mention.isValid()) {
                    Mention mentionPers = mentionService.store(mention, task);
                    mentions.add(mentionPers);
                }
            }
            for (Media medium : user.getEntities().getMedia()) {
                if (medium.isValid()) {
                    Media mediumPers = mediaService.store(medium, task);
                    media.add(mediumPers);
                }
            }
            for (TickerSymbol tickerSymbol : user.getEntities().getTickerSymbols()) {
                if (tickerSymbol.isValid()) {
                    TickerSymbol tickerSymbolPers = tickerSymbolService.store(tickerSymbol, task);
                    tickerSymbols.add(tickerSymbolPers);
                }
            }
            user.removeAllEntities();
            user = userService.store(user, task);

            user.getEntities().setUrls(urls);
            user.getEntities().setHashTags(hashTags);
            user.getEntities().setMentions(mentions);
            user.getEntities().setMedia(media);
            user.getEntities().setTickerSymbols(tickerSymbols);

            user = userService.store(user, task);

        } catch (Exception e){
            log.debug(msg+e.getMessage());
        }
        return user;
    }


    private static final Logger log = LoggerFactory.getLogger(StoreTwitterProfileForProxyMentionForUserImpl.class);

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserTransformService userTransformService;

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CountedEntitiesService countedEntitiesService;

    private final CreatePersistentUrl createPersistentUrl;

    public StoreTwitterProfileForProxyMentionForUserImpl(UserService userService, TwitterApiService twitterApiService, TaskService taskService, UserTransformService userTransformService, UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl, CountedEntitiesService countedEntitiesService) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userTransformService = userTransformService;
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
        this.countedEntitiesService = countedEntitiesService;
    }
}
