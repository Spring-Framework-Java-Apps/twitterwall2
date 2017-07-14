package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.ApiException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.oodm.service.entities.*;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.facade.StoreTwitterProfileForProxyMentionForUser;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;
import org.woehlke.twitterwall.scheduled.service.persist.impl.StoreEntitiesProcessImpl;
import org.woehlke.twitterwall.scheduled.service.persist.impl.StoreUserProcessImpl;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 14.07.17.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreTwitterProfileForProxyMentionForUserImpl implements StoreTwitterProfileForProxyMentionForUser {

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

    private final CreatePersistentUrl createPersistentUrl;

    public StoreTwitterProfileForProxyMentionForUserImpl(UserService userService, TwitterApiService twitterApiService, TaskService taskService, UserTransformService userTransformService, UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl) {
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
    }

    @Override
    public User storeTwitterProfileForProxyMentionForUser(Mention mention, Task task) {
        String msg = "storeTwitterProfileForProxyMentionForUser:";
        String screenName = mention.getScreenName();
        User foundUser = null;
        try {
            User myFoundUser = userService.findByScreenName(screenName);
            foundUser = myFoundUser;
        } catch (EmptyResultDataAccessException e){
            try {
                TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
                User myFoundUser = userTransformService.transform(twitterProfile);
                myFoundUser = this.storeUserProcess(myFoundUser, task);
                foundUser = myFoundUser;
            } catch (ApiException twitterApiException) {
                taskService.error(task,twitterApiException, msg);
                log.error(msg+twitterApiException.getMessage());
                return null;
            }
        }
        return foundUser;
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
        Entities entities = user.getEntities();

        /** @see StoreEntitiesProcessImpl.storeEntitiesProcess(Entities entities, Task task) */
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        Set<Media> media = new LinkedHashSet<>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<>();
        for (Url myUrl : entities.getUrls()) {
            if((myUrl != null)&&(myUrl.isValid())){
                Url urlPers = urlService.store(myUrl, task);
                urls.add(urlPers);
            } else if((myUrl != null)&&(myUrl.isRawUrlsFromDescription())){
                String urlStr = myUrl.getUrl();
                Url urlObj = createPersistentUrl.getPersistentUrlFor(urlStr,task);
                if((urlObj != null)&&(urlObj.isValid())){
                    urls.add(urlObj);
                }
            }
        }
        for (HashTag hashTag : entities.getTags()) {
            if(hashTag.isValid()){
                HashTag hashTagPers = hashTagService.store(hashTag, task);
                hashTags.add(hashTagPers);
            }
        }
        /** hier wird kein Proxy user mehr angelegt */
        for (Mention mention : entities.getMentions()) {
            if(mention.isValid()){
                Mention mentionPers =mentionService.store(mention, task);
                mentions.add(mentionPers);
            }
        }
        for(Media medium:entities.getMedia()){
            if(medium.isValid()) {
                Media mediumPers = mediaService.store(medium, task);
                media.add(mediumPers);
            }
        }
        for(TickerSymbol tickerSymbol:entities.getTickerSymbols()){
            if(tickerSymbol.isValid()){
                TickerSymbol tickerSymbolPers = tickerSymbolService.store(tickerSymbol,task);
                tickerSymbols.add(tickerSymbolPers);
            }
        }
        entities.setUrls(urls);
        entities.setTags(hashTags);
        entities.setMentions(mentions);
        entities.setMedia(media);
        entities.setTickerSymbols(tickerSymbols);

        user.setEntities(entities);
        user = userService.store(user,task);
        return user;
    }

}
