package org.woehlke.twitterwall.scheduled.service.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

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

    private final CreatePersistentUrl createPersistentUrl;

    @Autowired
    public StoreUserProcessImpl(UserService userService, UrlService urlService, HashTagService hashTagService, MentionService mentionService, CreatePersistentUrl createPersistentUrl) {
        this.userService = userService;
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.createPersistentUrl = createPersistentUrl;
    }

    @Override
    public User storeUserProcess(User user){
        String msg = "User.storeUserProcess ";
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        for (Url myUrl : user.getUrls()) {
            Url urlPers = createPersistentUrl.getPersistentUrlFor(myUrl.getUrl());
            if(urlPers != null){
                urls.add(urlPers);
            }
        }
        Url urlPers = createPersistentUrl.getPersistentUrlFor(user.getUrl());
        if(urlPers != null){
            urls.add(urlPers);
        }
        for (HashTag hashTag : user.getTags()) {
            hashTags.add(hashTagService.store(hashTag));
        }
        for (Mention mention : user.getMentions()) {
            mentions.add(mentionService.store(mention));
        }
        user.setUrls(urls);
        user.setTags(hashTags);
        user.setMentions(mentions);
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
