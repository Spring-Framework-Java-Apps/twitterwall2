package org.woehlke.twitterwall.oodm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User persist(User user) {
        return userRepository.persist(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
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
        return userRepository.findByIdTwitter(idTwitter);
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
    public User storeUser(User user) {
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
            User userPers = userRepository.findByIdTwitter(user.getIdTwitter());
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            log.info(msg+" try to update user "+user.toString());
            return userRepository.update(user);
        } catch (FindUserByIdTwitterException e) {
            log.info(msg+" try to persist user "+user.toString());
            return userRepository.persist(user);
        }
    }

    @Override
    public User transform(TwitterProfile twitterProfile) {
        long idTwitter = twitterProfile.getId();
        String screenName = twitterProfile.getScreenName();
        String name = twitterProfile.getName();
        String url = twitterProfile.getUrl();
        if (url == null || twitterProfile.getUrl().isEmpty()) {
            url = null;
        }
        String profileImageUrl = twitterProfile.getProfileImageUrl();
        String description = twitterProfile.getDescription();
        if (twitterProfile.getDescription().isEmpty()) {
            description = null;
        }
        String location = twitterProfile.getLocation();
        if (twitterProfile.getLocation().isEmpty()) {
            location = null;
        }
        Date createdDate = twitterProfile.getCreatedDate();
        User user = new User(idTwitter, screenName, name, url, profileImageUrl, description, location, createdDate);
        user.setTweeting(true);
        user.setLanguage(twitterProfile.getLanguage());
        user.setStatusesCount(twitterProfile.getStatusesCount());
        user.setFriendsCount(twitterProfile.getFriendsCount());
        user.setFollowersCount(twitterProfile.getFollowersCount());
        user.setFavoritesCount(twitterProfile.getFavoritesCount());
        user.setListedCount(twitterProfile.getListedCount());
        user.setFollowing(twitterProfile.isFollowing());
        user.setFollowRequestSent(twitterProfile.isFollowRequestSent());
        user.setProtected(twitterProfile.isProtected());
        user.setNotificationsEnabled(twitterProfile.isNotificationsEnabled());
        user.setVerified(twitterProfile.isVerified());
        user.setGeoEnabled(twitterProfile.isGeoEnabled());
        user.setContributorsEnabled(twitterProfile.isContributorsEnabled());
        user.setTranslator(twitterProfile.isTranslator());
        user.setTimeZone(twitterProfile.getTimeZone());
        user.setUtcOffset(twitterProfile.getUtcOffset());
        user.setSidebarBorderColor(twitterProfile.getSidebarBorderColor());
        user.setSidebarFillColor(twitterProfile.getSidebarFillColor());
        user.setBackgroundColor(twitterProfile.getBackgroundColor());
        user.setUseBackgroundImage(twitterProfile.useBackgroundImage());
        user.setBackgroundImageUrl(twitterProfile.getBackgroundImageUrl());
        user.setBackgroundImageTiled(twitterProfile.isBackgroundImageTiled());
        user.setTextColor(twitterProfile.getTextColor());
        user.setLinkColor(twitterProfile.getLinkColor());
        user.setShowAllInlineMedia(twitterProfile.showAllInlineMedia());
        user.setProfileBannerUrl(twitterProfile.getProfileBannerUrl());
        user = this.getEntitiesForUrlDescription(user);
        return user;
    }

    @Override
    public User getEntitiesForUrlDescription(User user) {
        String description = user.getDescription();
        user.setMentions(mentionService.getMentions(user));
        Set<Url> urls = urlService.getUrlsFor(user);
        user.setUrls(urls);
        user.setTags(hashTagService.getHashTagsFor(user));
        log.info("description " + description);
        log.info("++++++++++++++++++++");
        for (HashTag hashTag : user.getTags()) {
            log.info("found hashTag: " + hashTag.toString());
        }
        for (Url url : user.getUrls()) {
            log.info("found url: " + url.toString());
        }
        for (Mention mention : user.getMentions()) {
            log.info("found mention: " + mention.toString());
        }
        log.info("++++++++++++++++++++");
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public User storeUserProfile(TwitterProfile userProfile) {
        User user = this.transform(userProfile);
        user = this.storeUserProcess(user);
        return user;
    }
}
