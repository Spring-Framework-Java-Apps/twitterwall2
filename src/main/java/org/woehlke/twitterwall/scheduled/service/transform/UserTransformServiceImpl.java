package org.woehlke.twitterwall.scheduled.service.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.*;

import java.util.Date;
import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserTransformServiceImpl implements UserTransformService {

    private static final Logger log = LoggerFactory.getLogger(UserTransformServiceImpl.class);

    private final UrlTransformService urlTransformService;

    private final HashTagTransformService hashTagTransformService;

    private final MentionTransformService mentionTransformService;

    public UserTransformServiceImpl(UrlTransformService urlTransformService, HashTagTransformService hashTagTransformService, MentionTransformService mentionTransformService) {
        this.urlTransformService = urlTransformService;
        this.hashTagTransformService = hashTagTransformService;
        this.mentionTransformService = mentionTransformService;
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
        user =  getEntitiesForUrlDescription(user);
        return user;
    }

    @Override
    public User getEntitiesForUrlDescription(User user) {
        String description = user.getDescription();
        user.setMentions(mentionTransformService.findByUser(user));
        Set<Url> urls = urlTransformService.getUrlsFor(user);
        user.setUrls(urls);
        user.setTags(hashTagTransformService.getHashTagsFor(user));
        // TODO: mediaTransformService
        // TODO:  tickerSymbolTransformService
        log.debug("description " + description);
        log.debug("++++++++++++++++++++");
        for (HashTag hashTag : user.getTags()) {
            log.debug("found hashTag: " + hashTag.toString());
        }
        for (Url url : user.getUrls()) {
            log.debug("found url: " + url.toString());
        }
        for (Mention mention : user.getMentions()) {
            log.debug("found mention: " + mention.toString());
        }
        log.debug("++++++++++++++++++++");
        return user;
    }

}
