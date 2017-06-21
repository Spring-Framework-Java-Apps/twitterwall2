package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.exceptions.*;
import org.woehlke.twitterwall.oodm.service.*;
import org.woehlke.twitterwall.oodm.service.entities.*;

import java.util.*;

/**
 * Created by tw on 11.06.17.
 */
@Service
@javax.transaction.Transactional(javax.transaction.Transactional.TxType.NOT_SUPPORTED)
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
        myTweet=storeOneTweetPerform(myTweet);
        return myTweet;
    }

    private Tweet storeOneTweetPerform(Tweet tweet) {
        /** Retweeted Tweet */
        Tweet retweetedStatus = tweet.getRetweetedStatus();
        if (retweetedStatus != null) {
            retweetedStatus = this.storeOneTweetPerform(retweetedStatus);
            tweet.setRetweetedStatus(retweetedStatus);
        }
        /** The User */
        User user = tweet.getUser();
        user = this.storeOneUser(user);
        tweet.setUser(user);
        /** Tweet itself */
        Entities myEntities = tweet.getEntities();
        myEntities = this.storeEntities(myEntities);
        tweet.setEntities(myEntities);
        tweet = this.storeOneTweetItself(tweet);
        return tweet;
    }

    private Tweet storeOneTweetItself(Tweet tweet) {
        try {
            Tweet tweetPersistent = this.tweetService.findByIdTwitter(tweet.getIdTwitter());
            tweet.setId(tweetPersistent.getId());
            return this.tweetService.update(tweet);
        } catch (FindTweetByIdTwitterException e){
            return this.tweetService.persist(tweet);
        }
    }

    private User storeOneUser(User user) {
        try {
            User userPers = this.userService.findByIdTwitter(user.getIdTwitter());
            user.setId(userPers.getId());
            user.setFriend(userPers.isFriend());
            user.setFollower(userPers.isFollower());
            return this.userService.update(user);
        } catch (FindUserByIdTwitterException e){
            return this.userService.persist(user);
        }
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
            Entities myEntities = transformTwitterEntities(tweet.getEntities(),tweet.getId());
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
        if(twitterProfile.getLocation().isEmpty()){
            location=null;
        }
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

    private Entities transformTwitterEntities(org.springframework.social.twitter.api.Entities entities,long idTwitterFromTweet) {
        Set<Url> urls = transformTwitterEntitiesUrls(entities.getUrls());
        Set<HashTag> tags = transformTwitterEntitiesHashTags(entities.getHashTags());
        Set<Mention> mentions = transformTwitterEntitiesMentions(entities.getMentions());
        Set<Media> media = transformTwitterEntitiesMedia(entities.getMedia());
        Set<TickerSymbol> tickerSymbols = transformTwitterEntitiesTickerSymbols(entities.getTickerSymbols());
        Entities myEntities = new Entities(urls,tags,mentions,media,idTwitterFromTweet,tickerSymbols);
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
            try {
                TickerSymbol tickerSymbolPers = tickerSymbolService.findByTickerSymbolAndUrl(tickerSymbol.getTickerSymbol(), tickerSymbol.getUrl());
                tickerSymbolPers.setUrl(tickerSymbol.getUrl());
                tickerSymbolPers.setIndices(tickerSymbol.getIndices());
                tickerSymbolPers.setTickerSymbol(tickerSymbol.getTickerSymbol());
                tickerSymbolPers = tickerSymbolService.update(tickerSymbolPers);
                tickerSymbols.add(tickerSymbolPers);
            } catch (FindTickerSymbolByTickerSymbolAndUrlException e){
                tickerSymbol = tickerSymbolService.store(tickerSymbol);
                tickerSymbols.add(tickerSymbol);
            }
        }
        for(Mention mention:myEntities.getMentions()){
            try {
                Mention mentionPers = mentionService.findByScreenNameAndName(mention);
                mentionPers.setIndices(mention.getIndices());
                mentionPers.setIdTwitter(mention.getIdTwitter());
                mentionPers.setName(mention.getName());
                mentionPers.setScreenName(mention.getScreenName());
                mentionPers = mentionService.update(mentionPers);
                mentions.add(mentionPers);
            } catch (FindMentionByScreenNameAndNameException e){
                mention = mentionService.store(mention);
                mentions.add(mention);
            }
        }
        for(Media media:myEntities.getMedia()){
            try {
                Media mediaPers = mediaService.findByFields(media);
                mediaPers.setDisplay(media.getDisplay());
                mediaPers.setExpanded(media.getExpanded());
                mediaPers.setIdTwitter(media.getIdTwitter());
                mediaPers.setIndices(media.getIndices());
                mediaPers.setMediaHttp(media.getMediaHttp());
                mediaPers.setMediaHttps(media.getMediaHttps());
                mediaPers.setMediaType(media.getMediaType());
                mediaPers.setUrl(media.getUrl());
                mediaPers = mediaService.update(mediaPers);
                medias.add(mediaPers);
            } catch (FindMediaByFieldsExceptionException e){
                media = mediaService.store(media);
                medias.add(media);
            }
        }
        for(HashTag tag:myEntities.getTags()){
            try {
                HashTag tagPers = hashTagService.findByText(tag.getText());
                tagPers.setText(tag.getText());
                tagPers.setIndices(tag.getIndices());
                tagPers = hashTagService.update(tagPers);
                tags.add(tagPers);
            } catch (FindHashTagByTextException e){
                tag = hashTagService.store(tag);
                tags.add(tag);
            }
        }
        for(Url url:myEntities.getUrls()){
            try {
                Url urlPers = urlService.findByDisplayExpandedUrl(url.getDisplay(),url.getExpanded(),url.getUrl());
                urlPers.setIndices(url.getIndices());
                urlPers.setDisplay(url.getDisplay());
                urlPers.setExpanded(url.getExpanded());
                urlPers.setUrl(url.getUrl());
                urlPers = urlService.update(urlPers);
                urls.add(urlPers);
            } catch (FindUrlByDisplayExpandedUrlException e){
                url = urlService.store(url);
                urls.add(url);
            }
        }
        try {
            Entities myEntitiesPers = entitiesService.findByIdTwitterFromTweet(myEntities.getIdTwitterFromTweet());
            myEntitiesPers.setMedia(medias);
            myEntitiesPers.setMentions(mentions);
            myEntitiesPers.setTags(tags);
            myEntitiesPers.setTickerSymbols(tickerSymbols);
            myEntitiesPers.setUrls(urls);
            myEntitiesPers = entitiesService.update(myEntitiesPers);
            return myEntitiesPers;
        } catch (FindEntitiesByIdTwitterFromTweetException e){
            myEntities.setMedia(medias);
            myEntities.setMentions(mentions);
            myEntities.setTags(tags);
            myEntities.setTickerSymbols(tickerSymbols);
            myEntities.setUrls(urls);
            myEntities = entitiesService.store(myEntities);
            return myEntities;
        }
    }
}
