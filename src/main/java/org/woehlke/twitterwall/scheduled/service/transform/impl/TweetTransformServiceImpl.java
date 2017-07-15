package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.scheduled.service.transform.EntitiesTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.TweetTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

import java.util.Date;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TweetTransformServiceImpl implements TweetTransformService {

    private final UserTransformService userTransformService;

    private final EntitiesTransformService entitiesTransformService;

    @Autowired
    public TweetTransformServiceImpl(UserTransformService userTransformService, EntitiesTransformService entitiesTransformService) {
        this.userTransformService = userTransformService;
        this.entitiesTransformService = entitiesTransformService;
    }

    @Override
    public org.woehlke.twitterwall.oodm.entities.Tweet transform(org.springframework.social.twitter.api.Tweet tweetSource) {
        if (tweetSource == null) { return null; } else {
            org.woehlke.twitterwall.oodm.entities.Tweet retweetedStatus = transform(tweetSource.getRetweetedStatus());
            long idTwitter = tweetSource.getId();
            String idStr = tweetSource.getIdStr();
            String text = tweetSource.getText();
            Date createdAt = tweetSource.getCreatedAt();
            String fromUser = tweetSource.getFromUser();
            String profileImageUrl = tweetSource.getProfileImageUrl();
            Long toUserId = tweetSource.getToUserId();
            long fromUserId = tweetSource.getFromUserId();
            String languageCode = tweetSource.getLanguageCode();
            String source = tweetSource.getSource();
            org.woehlke.twitterwall.oodm.entities.Tweet tweetTarget = new org.woehlke.twitterwall.oodm.entities.Tweet(idTwitter, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
            tweetTarget.setFavoriteCount(tweetSource.getFavoriteCount());
            tweetTarget.setFavorited(tweetSource.isFavorited());
            tweetTarget.setInReplyToScreenName(tweetSource.getInReplyToScreenName());
            tweetTarget.setInReplyToUserId(tweetSource.getInReplyToUserId());
            tweetTarget.setLanguageCode(tweetSource.getLanguageCode());
            tweetTarget.setRetweetCount(tweetSource.getRetweetCount());
            tweetTarget.setRetweeted(tweetSource.isRetweeted());
            tweetTarget.setSource(tweetSource.getSource());
            tweetTarget.setFromUser(tweetSource.getFromUser());
            tweetTarget.setFavorited(tweetSource.isFavorited());
            tweetTarget.setInReplyToStatusId(tweetSource.getInReplyToStatusId());
            tweetTarget.setRetweetedStatus(retweetedStatus);
            TwitterProfile userSource = tweetSource.getUser();

            org.woehlke.twitterwall.oodm.entities.parts.Entities entitiesTarget = entitiesTransformService.transform(tweetSource.getEntities());

            tweetTarget.setEntities(entitiesTarget);

            /* transform userTarget */
            User userTarget = userTransformService.transform(userSource);
            tweetTarget.setUser(userTarget);

            return tweetTarget;
        }
    }
}
