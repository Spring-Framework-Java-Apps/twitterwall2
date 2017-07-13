package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.scheduled.service.transform.TweetTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.entities.*;

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
    public org.woehlke.twitterwall.oodm.entities.Tweet transform(org.springframework.social.twitter.api.Tweet tweet) {
        if (tweet == null) { return null; } else {
            org.woehlke.twitterwall.oodm.entities.Tweet retweetedStatus = transform(tweet.getRetweetedStatus());
            long idTwitter = tweet.getId();
            String idStr = tweet.getIdStr();
            String text = tweet.getText();
            Date createdAt = tweet.getCreatedAt();
            String fromUser = tweet.getFromUser();
            String profileImageUrl = tweet.getProfileImageUrl();
            Long toUserId = tweet.getToUserId();
            long fromUserId = tweet.getFromUserId();
            String languageCode = tweet.getLanguageCode();
            String source = tweet.getSource();
            org.woehlke.twitterwall.oodm.entities.Tweet myTweet = new org.woehlke.twitterwall.oodm.entities.Tweet(idTwitter, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
            myTweet.setFavoriteCount(tweet.getFavoriteCount());
            myTweet.setFavorited(tweet.isFavorited());
            myTweet.setInReplyToScreenName(tweet.getInReplyToScreenName());
            myTweet.setInReplyToUserId(tweet.getInReplyToUserId());
            myTweet.setLanguageCode(tweet.getLanguageCode());
            myTweet.setRetweetCount(tweet.getRetweetCount());
            myTweet.setRetweeted(tweet.isRetweeted());
            myTweet.setSource(tweet.getSource());
            myTweet.setFromUser(tweet.getFromUser());
            myTweet.setFavorited(tweet.isFavorited());
            myTweet.setInReplyToStatusId(tweet.getInReplyToStatusId());
            myTweet.setRetweetedStatus(retweetedStatus);
            TwitterProfile twitterProfile = tweet.getUser();

            org.woehlke.twitterwall.oodm.entities.Entities entities = entitiesTransformService.transform(tweet.getEntities());

            myTweet.setEntities(entities);

            /* transform user */
            User user = userTransformService.transform(twitterProfile);
            myTweet.setUser(user);

            return myTweet;
        }
    }
}
