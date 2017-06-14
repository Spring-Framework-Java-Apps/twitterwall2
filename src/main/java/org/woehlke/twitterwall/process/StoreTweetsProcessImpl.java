package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.service.*;
import org.woehlke.twitterwall.oodm.service.entities.*;

import java.util.*;

/**
 * Created by tw on 11.06.17.
 */
@Service
public class StoreTweetsProcessImpl implements StoreTweetsProcess {

    private static final Logger log = LoggerFactory.getLogger(StoreTweetsProcessImpl.class);

    private final TweetService tweetService;

    private final UserService userService;

    private final EntitiesService entitiesService;

    private final HashTagService hashTagService;

    private final MediaService mediaService;

    private final MentionService mentionService;

    private final TickerSymbolService tickerSymbolService;

    private final UrlService urlService;

    @Autowired
    public StoreTweetsProcessImpl(TweetService tweetService, UserService userService, EntitiesService entitiesService, HashTagService hashTagService, MediaService mediaService, MentionService mentionService, TickerSymbolService tickerSymbolService, UrlService urlService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.entitiesService = entitiesService;
        this.hashTagService = hashTagService;
        this.mediaService = mediaService;
        this.mentionService = mentionService;
        this.tickerSymbolService = tickerSymbolService;
        this.urlService = urlService;
    }

    @Override
    public Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet tweet) {
        Tweet myTweet = transformTweet(tweet);
        myTweet=storeOneTweet(myTweet);
        return myTweet;
    }

    private Tweet storeOneTweet(Tweet tweet) {
        //if(this.tweetService.isNotYetStored(tweet)) {
            /** The User */
            User user = tweet.getUser();
            user = storeOneUser(user);
            tweet.setUser(user);
            /** Retweeted Tweet */
            Tweet retweetedStatus = tweet.getRetweetedStatus();
            if (retweetedStatus != null) {
                retweetedStatus = storeOneTweet(retweetedStatus);
                tweet.setRetweetedStatus(retweetedStatus);
            }
            /** Tweet itself */
            Entities myEntities = tweet.getEntities();
            myEntities = storeEntities(myEntities);
            tweet.setEntities(myEntities);
            tweet = storeOneTweetItself(tweet);
        //}
        return tweet;
    }

    private Tweet storeOneTweetItself(Tweet tweet) {
        Tweet tweetPersistent = this.tweetService.findByIdTwitter(tweet.getIdTwitter());
        if(tweetPersistent != null){
            tweet.setId(tweetPersistent.getId());
            tweet = this.tweetService.update(tweet);
        } else {
            tweet = this.tweetService.persist(tweet);
        }
        return tweet;
    }

    private User storeOneUser(User user) {
        User userPers = this.userService.findByIdTwitter(user.getIdTwitter());
        if(userPers != null) {
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            user = this.userService.update(user);
        } else {
            user = this.userService.persist(user);
        }
        return user;
    }

    private Tweet transformTweet(org.springframework.social.twitter.api.Tweet tweet){
        if(tweet != null) {
            Tweet retweetedStatus = transformTweet(tweet.getRetweetedStatus());
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
            Tweet myTweet = new Tweet(idTwitter, idStr, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
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
            User user = transformTwitterProfile(twitterProfile);
            myTweet.setUser(user);
            Entities myEntities = transformTwitterEntities(tweet.getEntities());
            myTweet.setEntities(myEntities);
            return myTweet;
        } else {
            return null;
        }
    }

    private User transformTwitterProfile(TwitterProfile twitterProfile) {
        long idTwitter=twitterProfile.getId();
        String screenName=twitterProfile.getScreenName();
        String name=twitterProfile.getName();
        String url=twitterProfile.getUrl();
        String profileImageUrl=twitterProfile.getProfileImageUrl();
        String description=twitterProfile.getDescription();
        String location=twitterProfile.getLocation();
        Date createdDate=twitterProfile.getCreatedDate();
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
        return user;
    }

    @Override
    public User storeFollower(TwitterProfile follower) {
        User user = transformTwitterProfile(follower);
        user.setFollower(true);
        return storeOneUser(user);
    }

    @Override
    public User storeFriend(TwitterProfile friend) {
        User user = transformTwitterProfile(friend);
        user.setFriend(true);
        return storeOneUser(user);
    }

    private Entities transformTwitterEntities(org.springframework.social.twitter.api.Entities entities) {
        Set<Url> urls = transformTwitterEntitiesUrls(entities.getUrls());
        Set<HashTag> tags = transformTwitterEntitiesHashTags(entities.getHashTags());
        Set<Mention> mentions = transformTwitterEntitiesMentions(entities.getMentions());
        Set<Media> media = transformTwitterEntitiesMedia(entities.getMedia());
        Set<TickerSymbol> tickerSymbols = transformTwitterEntitiesTickerSymbols(entities.getTickerSymbols());
        Entities myEntities = new Entities(urls,tags,mentions,media,tickerSymbols);
        return myEntities;
    }

    private Set<TickerSymbol> transformTwitterEntitiesTickerSymbols(List<TickerSymbolEntity> tickerSymbols) {
        Set<TickerSymbol> myTickerSymbolEntities = new LinkedHashSet<TickerSymbol>();
        for(TickerSymbolEntity tickerSymbol:tickerSymbols){
            String tickerSymbolString = tickerSymbol.getTickerSymbol();
            String url = tickerSymbol.getUrl();
            int[] indices = tickerSymbol.getIndices();
            TickerSymbol myTickerSymbolEntity = new TickerSymbol(tickerSymbolString, url, indices);
            myTickerSymbolEntities.add(myTickerSymbolEntity);
        }
        return myTickerSymbolEntities;
    }

    private Set<Media> transformTwitterEntitiesMedia(List<MediaEntity> media) {
        Set<Media> myMediaEntities = new LinkedHashSet<Media>();
        for(MediaEntity medium:media){
            long idTwitter = medium.getId();
            String mediaHttp = medium.getMediaUrl();
            String mediaHttps = medium.getMediaSecureUrl();
            String url = medium.getUrl();
            String display = medium.getDisplayUrl();
            String expanded = medium.getExpandedUrl();
            String type = medium.getType();
            int[] indices = medium.getIndices();
            Media myMediaEntity = new Media(idTwitter, mediaHttp, mediaHttps, url, display, expanded, type, indices);
            myMediaEntities.add(myMediaEntity);
        }
        return myMediaEntities;
    }

    private Set<Mention> transformTwitterEntitiesMentions(List<MentionEntity> mentions) {
        Set<Mention> myMentionEntities = new LinkedHashSet<Mention>();
        for(MentionEntity mention:mentions){
            long idTwitter = mention.getId();
            String screenName = mention.getScreenName();
            String name = mention.getName();
            int[] indices = mention.getIndices();
            Mention myMentionEntity = new Mention(idTwitter, screenName, name, indices);
            myMentionEntities.add(myMentionEntity);
        }
        return myMentionEntities;
    }

    private Set<HashTag> transformTwitterEntitiesHashTags(List<HashTagEntity> hashTags) {
        Set<HashTag> myHashTagEntities = new LinkedHashSet<HashTag>();
        for(HashTagEntity hashTag:hashTags){
            String text = hashTag.getText();
            int[] indices = hashTag.getIndices();
            HashTag myHashTagEntity = new HashTag(text, indices);
            myHashTagEntities.add(myHashTagEntity);
        }
        return myHashTagEntities;
    }

    private Set<Url> transformTwitterEntitiesUrls(List<UrlEntity> urls) {
        Set<Url> myUrls = new LinkedHashSet<Url>();
        for(UrlEntity url:urls){
            String display = url.getDisplayUrl();
            String expanded = url.getExpandedUrl();
            String urlStr = url.getUrl();
            int[] indices = url.getIndices();
            Url myUrlEntity = new Url(display, expanded, urlStr, indices);
            myUrls.add(myUrlEntity);
        }
        return myUrls;
    }

    private Entities storeEntities(Entities myEntities) {
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> tags = new LinkedHashSet<HashTag>();
        Set<Mention> mentions = new LinkedHashSet<Mention>();
        Set<Media> medias = new LinkedHashSet<Media>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
        for(TickerSymbol tickerSymbol:myEntities.getTickerSymbols()){
            tickerSymbol= tickerSymbolService.store(tickerSymbol);
            tickerSymbols.add(tickerSymbol);
        }
        for(Mention mention:myEntities.getMentions()){
            Mention mentionPers = mentionService.findByIdTwitter(mention.getIdTwitter());
            if(mentionPers != null){
                mention.setId(mentionPers.getId());
                mention= mentionService.update(mention);
            } else {
                mention= mentionService.store(mention);
            }
            mentions.add(mention);
        }
        for(Media media:myEntities.getMedia()){
            Media mediaPers = mediaService.findByIdTwitter(media.getIdTwitter());
            if(mediaPers != null){
                media.setId(mediaPers.getId());
                media= mediaService.update(media);
            } else {
                media= mediaService.store(media);
            }
            medias.add(media);
        }
        for(HashTag tag:myEntities.getTags()){
            tag= hashTagService.store(tag);
            tags.add(tag);
        }
        for(Url url:myEntities.getUrls()){
            url= urlService.store(url);
            urls.add(url);
        }
        myEntities.setMedia(medias);
        myEntities.setMentions(mentions);
        myEntities.setTags(tags);
        myEntities.setTickerSymbols(tickerSymbols);
        myEntities.setUrls(urls);
        myEntities= entitiesService.store(myEntities);
        return myEntities;
    }
}
