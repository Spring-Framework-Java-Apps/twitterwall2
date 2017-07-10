package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.*;
import org.woehlke.twitterwall.scheduled.service.persist.CreatePersistentUrl;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProcessImpl implements StoreUserProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProcessImpl.class);

    private final UserService userService;

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    @Autowired
    public StoreUserProcessImpl(UserService userService, UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl) {
        this.userService = userService;
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
    }

    @Override
    public User storeUserProcess(User user, Task task){
        String msg = "User.storeUserProcess ";
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        Set<Media> media = new LinkedHashSet<Media>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
        for (Url myUrl : user.getUrls()) {
            Url urlPers = createPersistentUrl.getPersistentUrlFor(myUrl.getUrl(),task);
            if((urlPers != null)&&(urlPers.isValid())){
                urls.add(urlPers);
            }
        }
        Url urlPers = createPersistentUrl.getPersistentUrlFor(user.getUrl(), task);
        if((urlPers != null)&&(urlPers.isValid())){
            urls.add(urlPers);
        }
        for (HashTag hashTag : user.getTags()) {
            if(hashTag.isValid()){
                hashTags.add(hashTagService.store(hashTag, task));
            }
        }
        for (Mention mention : user.getMentions()) {
            if(mention.isValid()){
                mentions.add(mentionService.store(mention, task));
            }
        }
        for(Media medium:user.getMedia()){
            if(medium.isValid()){
                media.add(mediaService.store(medium,task));
            }
        }
        for(TickerSymbol tickerSymbol:user.getTickerSymbols()){
            if(tickerSymbol.isValid()){
                tickerSymbols.add(tickerSymbolService.store(tickerSymbol,task));
            }
        }
        user.setUrls(urls);
        user.setTags(hashTags);
        user.setMentions(mentions);
        user.setMedia(media);
        user.setTickerSymbols(tickerSymbols);
        try {
            long idTwitter = user.getIdTwitter();
            User userPers = userService.findByIdTwitter(idTwitter);
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            if(!user.isOnDefinedUserList()){
                user.setOnDefinedUserList(userPers.isOnDefinedUserList());
            }
            log.debug(msg+" try to update user "+user.toString());
            return userService.update(user);
        } catch (EmptyResultDataAccessException e) {
            log.debug(msg+" try to persist user "+user.toString());
            return userService.create(user);
        }
    }
}
