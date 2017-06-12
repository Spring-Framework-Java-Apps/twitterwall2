package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.service.*;

import java.util.*;

/**
 * Created by tw on 11.06.17.
 */
@Service
public class StoreTweetsProcessImpl implements StoreTweetsProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreTweetsProcessImpl.class);

    private final MyTweetService myTweetService;

    private final MyTwitterProfileService myTwitterProfileService;

    private final MyEntitiesService myEntitiesService;

    private final MyHashTagEntityService myHashTagEntityService;

    private final MyMediaEntityService myMediaEntityService;

    private final MyMentionEntityService myMentionEntityService;

    private final MyTickerSymbolEntityService myTickerSymbolEntityService;

    private final MyUrlEntityService myUrlEntityService;

    @Autowired
    public StoreTweetsProcessImpl(MyTweetService myTweetService, MyTwitterProfileService myTwitterProfileService, MyEntitiesService myEntitiesService, MyHashTagEntityService myHashTagEntityService, MyMediaEntityService myMediaEntityService, MyMentionEntityService myMentionEntityService, MyTickerSymbolEntityService myTickerSymbolEntityService, MyUrlEntityService myUrlEntityService) {
        this.myTweetService = myTweetService;
        this.myTwitterProfileService = myTwitterProfileService;
        this.myEntitiesService = myEntitiesService;
        this.myHashTagEntityService = myHashTagEntityService;
        this.myMediaEntityService = myMediaEntityService;
        this.myMentionEntityService = myMentionEntityService;
        this.myTickerSymbolEntityService = myTickerSymbolEntityService;
        this.myUrlEntityService = myUrlEntityService;
    }

    @Override
    public MyTweet storeOneTweet(Tweet tweet) {
        MyTweet myTweet = transformTweet(tweet);
        myTweet=storeOneTweet(myTweet);
        return myTweet;
    }

    private MyTweet storeOneTweet(MyTweet tweet) {
        /** The User */
        MyTwitterProfile user = tweet.getUser();
        user=storeOneUser(user);
        tweet.setUser(user);
        /** Retweeted Tweet */
        MyTweet retweetedStatus = tweet.getRetweetedStatus();
        if(retweetedStatus!=null){
            retweetedStatus=storeOneTweet(retweetedStatus);
            tweet.setRetweetedStatus(retweetedStatus);
        }
        /** Tweet itself */
        MyEntities myEntities = tweet.getEntities();
        myEntities = storeEntities(myEntities);
        tweet.setEntities(myEntities);
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
            MyEntities myEntities = transformTwitterEntities(tweet.getEntities());
            myTweet.setEntities(myEntities);
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

    private MyEntities transformTwitterEntities(Entities entities) {
        Set<MyUrlEntity> urls = transformTwitterEntitiesUrls(entities.getUrls());
        Set<MyHashTagEntity> tags = transformTwitterEntitiesHashTags(entities.getHashTags());
        Set<MyMentionEntity> mentions = transformTwitterEntitiesMentions(entities.getMentions());
        Set<MyMediaEntity> media = transformTwitterEntitiesMedia(entities.getMedia());
        Set<MyTickerSymbolEntity> tickerSymbols = transformTwitterEntitiesTickerSymbols(entities.getTickerSymbols());
        MyEntities myEntities = new MyEntities(urls,tags,mentions,media,tickerSymbols);
        return myEntities;
    }

    private Set<MyTickerSymbolEntity> transformTwitterEntitiesTickerSymbols(List<TickerSymbolEntity> tickerSymbols) {
        Set<MyTickerSymbolEntity> myTickerSymbolEntities = new LinkedHashSet<MyTickerSymbolEntity>();
        for(TickerSymbolEntity tickerSymbol:tickerSymbols){
            String tickerSymbolString = tickerSymbol.getTickerSymbol();
            String url = tickerSymbol.getUrl();
            int[] indices = tickerSymbol.getIndices();
            MyTickerSymbolEntity myTickerSymbolEntity = new MyTickerSymbolEntity(tickerSymbolString, url, indices);
            myTickerSymbolEntities.add(myTickerSymbolEntity);
        }
        return myTickerSymbolEntities;
    }

    private Set<MyMediaEntity> transformTwitterEntitiesMedia(List<MediaEntity> media) {
        Set<MyMediaEntity> myMediaEntities = new LinkedHashSet<MyMediaEntity>();
        for(MediaEntity medium:media){
            long idTwitter = medium.getId();
            String mediaHttp = medium.getMediaUrl();
            String mediaHttps = medium.getMediaSecureUrl();
            String url = medium.getUrl();
            String display = medium.getDisplayUrl();
            String expanded = medium.getExpandedUrl();
            String type = medium.getType();
            int[] indices = medium.getIndices();
            MyMediaEntity myMediaEntity = new MyMediaEntity(idTwitter, mediaHttp, mediaHttps, url, display, expanded, type, indices);
            myMediaEntities.add(myMediaEntity);
        }
        return myMediaEntities;
    }

    private Set<MyMentionEntity> transformTwitterEntitiesMentions(List<MentionEntity> mentions) {
        Set<MyMentionEntity> myMentionEntities = new LinkedHashSet<MyMentionEntity>();
        for(MentionEntity mention:mentions){
            long idTwitter = mention.getId();
            String screenName = mention.getScreenName();
            String name = mention.getName();
            int[] indices = mention.getIndices();
            MyMentionEntity myMentionEntity = new MyMentionEntity(idTwitter, screenName, name, indices);
            myMentionEntities.add(myMentionEntity);
        }
        return myMentionEntities;
    }

    private Set<MyHashTagEntity> transformTwitterEntitiesHashTags(List<HashTagEntity> hashTags) {
        Set<MyHashTagEntity> myHashTagEntities = new LinkedHashSet<MyHashTagEntity>();
        for(HashTagEntity hashTag:hashTags){
            String text = hashTag.getText();
            int[] indices = hashTag.getIndices();
            MyHashTagEntity myHashTagEntity = new MyHashTagEntity(text, indices);
            myHashTagEntities.add(myHashTagEntity);
        }
        return myHashTagEntities;
    }

    private Set<MyUrlEntity> transformTwitterEntitiesUrls(List<UrlEntity> urls) {
        Set<MyUrlEntity> myUrls = new LinkedHashSet<MyUrlEntity>();
        for(UrlEntity url:urls){
            String display = url.getDisplayUrl();
            String expanded = url.getExpandedUrl();
            String urlStr = url.getUrl();
            int[] indices = url.getIndices();
            MyUrlEntity myUrlEntity = new MyUrlEntity(display, expanded, urlStr, indices);
            myUrls.add(myUrlEntity);
        }
        return myUrls;
    }

    private MyEntities storeEntities(MyEntities myEntities) {
        Set<MyUrlEntity> urls = new LinkedHashSet<>();
        Set<MyHashTagEntity> tags = new LinkedHashSet<MyHashTagEntity>();
        Set<MyMentionEntity> mentions = new LinkedHashSet<MyMentionEntity>();
        Set<MyMediaEntity> medias = new LinkedHashSet<MyMediaEntity>();
        Set<MyTickerSymbolEntity> tickerSymbols = new LinkedHashSet<MyTickerSymbolEntity>();
        for(MyTickerSymbolEntity tickerSymbol:myEntities.getTickerSymbols()){
            tickerSymbol=myTickerSymbolEntityService.store(tickerSymbol);
            tickerSymbols.add(tickerSymbol);
        }
        for(MyMentionEntity mention:myEntities.getMentions()){
            MyMentionEntity mentionPers = myMentionEntityService.findByIdTwitter(mention.getIdTwitter());
            if(mentionPers != null){
                mention.setId(mentionPers.getId());
                mention=myMentionEntityService.update(mention);
            } else {
                mention=myMentionEntityService.store(mention);
            }
            mentions.add(mention);
        }
        for(MyMediaEntity media:myEntities.getMedia()){
            MyMediaEntity mediaPers = myMediaEntityService.findByIdTwitter(media.getIdTwitter());
            if(mediaPers != null){
                media.setId(mediaPers.getId());
                media=myMediaEntityService.update(media);
            } else {
                media=myMediaEntityService.store(media);
            }
            medias.add(media);
        }
        for(MyHashTagEntity tag:myEntities.getTags()){
            tag=myHashTagEntityService.store(tag);
            tags.add(tag);
        }
        for(MyUrlEntity url:myEntities.getUrls()){
            url=myUrlEntityService.store(url);
            urls.add(url);
        }
        myEntities.setMedia(medias);
        myEntities.setMentions(mentions);
        myEntities.setTags(tags);
        myEntities.setTickerSymbols(tickerSymbols);
        myEntities.setUrls(urls);
        myEntities=myEntitiesService.store(myEntities);
        return myEntities;
    }
}
