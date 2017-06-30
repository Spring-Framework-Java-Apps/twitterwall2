package org.woehlke.twitterwall.oodm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByScreenNameException;
import org.woehlke.twitterwall.oodm.repository.UserRepository;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final MentionService mentionService;

    private final HashTagService hashTagService;

    private final UrlService urlService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MentionService mentionService, HashTagService hashTagService, UrlService urlService) {
        this.userRepository = userRepository;
        this.mentionService = mentionService;
        this.hashTagService = hashTagService;
        this.urlService = urlService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User create(User user) {
        return userRepository.persist(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll(User.class);
    }

    @Override
    public long count() {
        return userRepository.count(User.class);
    }

    @Override
    public User findByScreenName(String screenName) {
        if (!User.isValidScreenName(screenName)) {
            throw new FindUserByScreenNameException(screenName);
        }
        return userRepository.findByScreenName(screenName);
    }

    @Override
    public List<User> getTweetingUsers() {
        return userRepository.getTweetingUsers();
    }

    @Override
    public List<User> getNotYetFriendUsers() {
        return userRepository.getNotYetFriendUsers();
    }

    @Override
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter,User.class);
    }

    @Override
    public List<String> getAllDescriptions() {
        return userRepository.getAllDescriptions();
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return userRepository.getAllTwitterIds();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User store(User user) {
        return storeUserProcess(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User storeUserProcess(User user){
        String msg = "User.storeUserProcess ";
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> hashTags = new LinkedHashSet<>();
        Set<Mention> mentions = new LinkedHashSet<>();
        for (Url myUrl : user.getUrls()) {
            urls.add(urlService.getPersistentUrlFor(myUrl.getUrl()));
        }
        urls.add(urlService.getPersistentUrlFor(user.getUrl()));
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
            User userPers = userRepository.findByIdTwitter(user.getIdTwitter(),User.class);
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            log.info(msg+" try to update user "+user.toString());
            return userRepository.update(user);
        } catch (EmptyResultDataAccessException e) {
            log.info(msg+" try to persist user "+user.toString());
            return userRepository.persist(user);
        }
    }
}
