package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.entities.*;

import java.util.Date;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserTransformServiceImpl implements UserTransformService {

    private static final Logger log = LoggerFactory.getLogger(UserTransformServiceImpl.class);


    private final EntitiesTransformService entitiesTransformService;

    @Autowired
    public UserTransformServiceImpl(EntitiesTransformService entitiesTransformService) {
        this.entitiesTransformService = entitiesTransformService;
    }

    @Override
    public User transform(TwitterProfile twitterProfile) {
        String msg = "User.transform for "+twitterProfile.getId();
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
        Entities entities = this.entitiesTransformService.getEntitiesForUser(user);
        log.debug(msg+" entities = "+entities.toString());
        user.setEntities(entities);
        log.debug(msg+" user = "+user.toString());
        return user;
    }

}
