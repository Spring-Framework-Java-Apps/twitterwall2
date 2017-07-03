package org.woehlke.twitterwall.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.frontend.model.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.*;
import org.woehlke.twitterwall.scheduled.service.TweetTransformService;
import org.woehlke.twitterwall.scheduled.service.UserTransformService;

import javax.persistence.NoResultException;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class PersistDataFromTwitterImpl implements PersistDataFromTwitter {

    private static final Logger log = LoggerFactory.getLogger(PersistDataFromTwitterImpl.class);

    private final UserService userService;

    private final TweetService tweetService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final HashTagService hashTagService;

    private final UrlService urlService;

    private final UrlCacheService urlCacheService;

    private final TickerSymbolService tickerSymbolService;

    private final TweetTransformService tweetTransformService;

    private final UserTransformService userTransformService;

    private final TwitterApiService twitterApiService;

    @Autowired
    public PersistDataFromTwitterImpl(UserService userService, TweetService tweetService, MentionService mentionService, MediaService mediaService, HashTagService hashTagService, UrlService urlService, UrlCacheService urlCacheService, TickerSymbolService tickerSymbolService, TweetTransformService tweetTransformService, UserTransformService userTransformService, TwitterApiService twitterApiService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.hashTagService = hashTagService;
        this.urlService = urlService;
        this.urlCacheService = urlCacheService;
        this.tickerSymbolService = tickerSymbolService;
        this.tweetTransformService = tweetTransformService;
        this.userTransformService = userTransformService;
        this.twitterApiService = twitterApiService;
    }

    @Override
    public Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet myTweet) {
        Tweet tweet = tweetTransformService.transform(myTweet);
        tweet = this.storeOneTweetPerform(tweet);
        return tweet;
    }

    /** private Method because of recursive Method Call in this Method **/
    private Tweet storeOneTweetPerform(Tweet tweet){
        /** Retweeted Tweet */
        Tweet retweetedStatus = tweet.getRetweetedStatus();
        if (retweetedStatus != null) {
            /** private Method because of recursive Method Call in this Method **/
            retweetedStatus = this.storeOneTweetPerform(retweetedStatus);
            tweet.setRetweetedStatus(retweetedStatus);
        }
        /** The User */
        User user = tweet.getUser();
        user.setOnDefinedUserList(false);
        user = userService.storeUserProcess(user);
        tweet.setUser(user);
        /** The Entities */
        Set<Url> urls = new LinkedHashSet<>();
        Set<HashTag> tags = new LinkedHashSet<HashTag>();
        Set<Mention> mentions = new LinkedHashSet<Mention>();
        Set<Media> media = new LinkedHashSet<Media>();
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
        for (TickerSymbol tickerSymbol : tweet.getTickerSymbols()) {
            tickerSymbols.add(tickerSymbolService.storeTickerSymbol(tickerSymbol));
        }
        for (Mention mention : tweet.getMentions()) {
            mentions.add(mentionService.store(mention));
             try {
                 User userFromMention = storeUserProfileForScreenName(mention.getScreenName());
             } catch (IllegalArgumentException exe){
             }
        }
        for (Media medium : tweet.getMedia()) {
            media.add(mediaService.store(medium));
        }
        for (HashTag hashTag : tweet.getTags()) {
            tags.add(hashTagService.store(hashTag));
        }
        for (Url url : tweet.getUrls()) {
            urls.add(urlService.getPersistentUrlFor(url.getUrl()));
        }
        tweet.setUrls(urls);
        tweet.setTags(tags);
        tweet.setMentions(mentions);
        tweet.setMedia(media);
        tweet.setTickerSymbols(tickerSymbols);
        /** Tweet itself */
        tweet = tweetService.store(tweet);
        return tweet;
    }

    private User storeUserProfileForScreenName(String screenName){
        String msg = "storeUserProfile for ScreenName = "+screenName+" ";
        if(screenName != null && !screenName.isEmpty()) {
            try {
                User userPersForMention = this.userService.findByScreenName(screenName);
                return userPersForMention;
            } catch (EmptyResultDataAccessException e) {
                try {
                    TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                    User userFromMention = this.storeUserProfile(twitterProfile);
                    log.info(msg + " userFromScreenName: "+userFromMention.toString());
                    return userFromMention;
                } catch (RateLimitExceededException ex) {
                    log.warn(msg + ex.getMessage());
                    Throwable t = ex.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    throw new TwitterApiException(msg+screenName, ex);
                }
            }
        } else  {
            throw new IllegalArgumentException("screenName is empty");
        }
    }

    @Override
    public User storeUserProfile(TwitterProfile userProfile) {
        String msg = "storeUserProfile: ";
        User user = userTransformService.transform(userProfile);
        user.setOnDefinedUserList(false);
        user = userService.storeUserProcess(user);
        for(Mention mention:user.getMentions()){
            String screenName = mention.getScreenName();
            User userFromMention = storeUserProfileForScreenName(screenName);
        }
        return user;
    }

    @Override
    public User storeUserProfileForUserList(TwitterProfile twitterProfile) {
        String msg = "storeUserProfileForUserList: ";
        User user = userTransformService.transform(twitterProfile);
        user.setOnDefinedUserList(true);
        user = userService.storeUserProcess(user);
        for(Mention mention:user.getMentions()){
            String screenName = mention.getScreenName();
            User userFromMention = storeUserProfileForScreenName(screenName);
        }
        return user;
    }

    @Override
    public long countTweets() {
        return tweetService.count();
    }

    @Override
    public long countUsers() {
        return this.userService.count();
    }

    @Override
    public User findUserByScreenName(String screenName) {
        return this.userService.findByScreenName(screenName);
    }

    @Override
    public CountedEntities countAll() {
        CountedEntities c = new CountedEntities();
        c.setCountHashTags(this.hashTagService.count());
        c.setCountMedia(this.mediaService.count());
        c.setCountMention(this.mentionService.count());
        c.setCountTickerSymbol(this.tickerSymbolService.count());
        c.setCountTweets(this.tweetService.count());
        c.setCountUrl(this.urlService.count());
        c.setCountUrlCache(this.urlCacheService.count());
        c.setCountUser(this.userService.count());
        log.info("countAll: "+c.toString());
        return c;
    }

}
