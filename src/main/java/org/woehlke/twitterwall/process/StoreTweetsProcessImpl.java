package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.MyTweet;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;
import org.woehlke.twitterwall.oodm.service.MyTweetService;
import org.woehlke.twitterwall.oodm.service.MyTwitterProfileService;

import java.util.Date;

/**
 * Created by tw on 11.06.17.
 */
@Service
public class StoreTweetsProcessImpl implements StoreTweetsProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreTweetsProcessImpl.class);


    private final MyTweetService myTweetService;

    private final MyTwitterProfileService myTwitterProfileService;

    @Autowired
    public StoreTweetsProcessImpl(MyTweetService myTweetService, MyTwitterProfileService myTwitterProfileService) {
        this.myTweetService = myTweetService;
        this.myTwitterProfileService = myTwitterProfileService;
    }

    @Override
    public MyTweet storeOneTweet(Tweet tweet) {
        MyTweet myTweet = transformTweet(tweet);
        myTweet=storeOneTweet(myTweet);
        return myTweet;
    }

    private MyTweet storeOneTweet(MyTweet tweet) {
        //The User
        MyTwitterProfile user = tweet.getUser();
        user=storeOneUser(user);
        tweet.setUser(user);
        // Retweeted Tweet
        MyTweet retweetedStatus = tweet.getRetweetedStatus();
        if(retweetedStatus!=null){
            retweetedStatus=storeOneTweet(retweetedStatus);
            tweet.setRetweetedStatus(retweetedStatus);
        }
        //TODO: store Twitter-Entities
        // Tweet itself
        tweet=storeOneTweetItself(tweet);
        return tweet;
    }

    private MyTweet storeOneTweetItself(MyTweet tweet) {
        MyTweet tweetPersistent = this.myTweetService.findByIdTwitter(tweet.getIdTwitter());
        if(tweetPersistent != null){
            tweet.setId(tweetPersistent.getId());
            tweet = this.myTweetService.update(tweet);
        } else {
            tweet = this.myTweetService.persist(tweet);
        }
        return tweet;
    }

    private MyTwitterProfile storeOneUser(MyTwitterProfile user) {
        MyTwitterProfile userPers = this.myTwitterProfileService.findByIdTwitter(user.getIdTwitter());
        if(userPers != null) {
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            user = this.myTwitterProfileService.update(user);
        } else {
            user = this.myTwitterProfileService.persist(user);
        }
        return user;
    }

    private MyTweet transformTweet(Tweet tweet){
        if(tweet != null) {
            MyTweet retweetedStatus = transformTweet(tweet.getRetweetedStatus());
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
            MyTweet myTweet = new MyTweet(idTwitter, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
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
            MyTwitterProfile myTwitterProfile  = transformTwitterProfile(twitterProfile);
            myTweet.setUser(myTwitterProfile);
            //TODO: transform Twitter-Entities
            //myTweet.setEntities();
            return myTweet;
        } else {
            return null;
        }
    }

    private MyTwitterProfile transformTwitterProfile(TwitterProfile twitterProfile) {
        long idTwitter=twitterProfile.getId();
        String screenName=twitterProfile.getScreenName();
        String name=twitterProfile.getName();
        String url=twitterProfile.getUrl();
        String profileImageUrl=twitterProfile.getProfileImageUrl();
        String description=twitterProfile.getDescription();
        String location=twitterProfile.getLocation();
        Date createdDate=twitterProfile.getCreatedDate();
        MyTwitterProfile myTwitterProfile = new MyTwitterProfile(idTwitter, screenName, name, url, profileImageUrl, description, location, createdDate);
        myTwitterProfile.setLanguage(twitterProfile.getLanguage());
        myTwitterProfile.setStatusesCount(twitterProfile.getStatusesCount());
        myTwitterProfile.setFriendsCount(twitterProfile.getFriendsCount());
        myTwitterProfile.setFollowersCount(twitterProfile.getFollowersCount());
        myTwitterProfile.setFavoritesCount(twitterProfile.getFavoritesCount());
        myTwitterProfile.setListedCount(twitterProfile.getListedCount());
        myTwitterProfile.setFollowing(twitterProfile.isFollowing());
        myTwitterProfile.setFollowRequestSent(twitterProfile.isFollowRequestSent());
        myTwitterProfile.setProtected(twitterProfile.isProtected());
        myTwitterProfile.setNotificationsEnabled(twitterProfile.isNotificationsEnabled());
        myTwitterProfile.setVerified(twitterProfile.isVerified());
        myTwitterProfile.setGeoEnabled(twitterProfile.isGeoEnabled());
        myTwitterProfile.setContributorsEnabled(twitterProfile.isContributorsEnabled());
        myTwitterProfile.setTranslator(twitterProfile.isTranslator());
        myTwitterProfile.setTimeZone(twitterProfile.getTimeZone());
        myTwitterProfile.setUtcOffset(twitterProfile.getUtcOffset());
        myTwitterProfile.setSidebarBorderColor(twitterProfile.getSidebarBorderColor());
        myTwitterProfile.setSidebarFillColor(twitterProfile.getSidebarFillColor());
        myTwitterProfile.setBackgroundColor(twitterProfile.getBackgroundColor());
        myTwitterProfile.setUseBackgroundImage(twitterProfile.useBackgroundImage());
        myTwitterProfile.setBackgroundImageUrl(twitterProfile.getBackgroundImageUrl());
        myTwitterProfile.setBackgroundImageTiled(twitterProfile.isBackgroundImageTiled());
        myTwitterProfile.setTextColor(twitterProfile.getTextColor());
        myTwitterProfile.setLinkColor(twitterProfile.getLinkColor());
        myTwitterProfile.setShowAllInlineMedia(twitterProfile.showAllInlineMedia());
        myTwitterProfile.setProfileBannerUrl(twitterProfile.getProfileBannerUrl());
        return myTwitterProfile;
    }

    @Override
    public MyTwitterProfile storeFollower(TwitterProfile follower) {
        MyTwitterProfile myTwitterProfile = transformTwitterProfile(follower);
        myTwitterProfile.setFollower(true);
        return storeOneUser(myTwitterProfile);
    }

    @Override
    public MyTwitterProfile storeFriend(TwitterProfile friend) {
        MyTwitterProfile myTwitterProfile = transformTwitterProfile(friend);
        myTwitterProfile.setFriend(true);
        return storeOneUser(myTwitterProfile);
    }
}
