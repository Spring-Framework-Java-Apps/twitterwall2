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

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    private final CreatePersistentUrl createPersistentUrl;

    @Autowired
    public StoreUserProcessImpl(UserService userService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService, CreatePersistentUrl createPersistentUrl) {
        this.userService = userService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
        this.createPersistentUrl = createPersistentUrl;
    }

    @Override
    public User storeUserProcess(User user, Task task){
        String msg = "User.storeUserProcess ";
        Set<Url> urls = user.getUrls();
        Set<HashTag> hashTags = user.getTags();
        Set<Mention> mentions = user.getMentions();
        Set<Media> media = user.getMedia();
        Set<TickerSymbol> tickerSymbols = user.getTickerSymbols();
        user.removeAllUrls();
        user.removeAllTags();
        user.removeAllMentions();
        user.removeAllMedia();
        user.removeAllTickerSymbols();
        user = userService.store(user,task);
        for (Url myUrl : urls) {
            Url urlPers = createPersistentUrl.getPersistentUrlFor(myUrl.getUrl(),task);
            if((urlPers != null)&&(urlPers.isValid())){
                user.addUrl(urlPers);
            }
        }
        Url urlPers = createPersistentUrl.getPersistentUrlFor(user.getUrl(), task);
        if((urlPers != null)&&(urlPers.isValid())){
            user.addUrl(urlPers);
        }
        for (HashTag hashTag : hashTags) {
            if(hashTag.isValid()){
                HashTag hashTagPers = hashTagService.store(hashTag, task);
                user.addTag(hashTagPers);
            }
        }
        for (Mention mention : mentions) {
            if(mention.isValid()){
                user.addMention(mentionService.store(mention, task));
            }
        }
        for(Media medium:media){
            if(medium.isValid()){
                user.addMedium(mediaService.store(medium,task));
            }
        }
        for(TickerSymbol tickerSymbol:tickerSymbols){
            if(tickerSymbol.isValid()){
                user.addTickerSymbol(tickerSymbolService.store(tickerSymbol,task));
            }
        }
        user = userService.store(user,task);
        return user;
        /*
        try {
            long idTwitter = user.getIdTwitter();
            User userPers = userService.findByIdTwitter(idTwitter);
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            user.setOnDefinedUserList(userPers.isOnDefinedUserList());
            log.debug(msg+" try to update user "+user.toString());
            return userService.update(user);
        } catch (EmptyResultDataAccessException e) {
            log.debug(msg+" try to persist user "+user.toString());
            return userService.create(user);
        }
        */
    }
}
