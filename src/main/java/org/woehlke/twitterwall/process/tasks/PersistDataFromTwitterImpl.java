package org.woehlke.twitterwall.process.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.*;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.process.backend.service.TweetTransformService;
import org.woehlke.twitterwall.process.backend.service.UserTransformService;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class PersistDataFromTwitterImpl implements PersistDataFromTwitter,PersistDataFromTwitterTest {

    private static final Logger log = LoggerFactory.getLogger(PersistDataFromTwitterImpl.class);
    
    private final UserService userService;
    
    private final TwitterApiService twitterApiService;

    private final TweetService tweetService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final HashTagService hashTagService;

    private final UrlService urlService;

    private final TickerSymbolService tickerSymbolService;

    private final TweetTransformService tweetTransformService;

    private final UserTransformService userTransformService;

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.backend.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Autowired
    public PersistDataFromTwitterImpl(UserService userService, TwitterApiService twitterApiService, TweetService tweetService, MentionService mentionService, MediaService mediaService, HashTagService hashTagService, UrlService urlService, TickerSymbolService tickerSymbolService, TweetTransformService tweetTransformService, UserTransformService userTransformService) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.tweetService = tweetService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.hashTagService = hashTagService;
        this.urlService = urlService;
        this.tickerSymbolService = tickerSymbolService;
        this.tweetTransformService = tweetTransformService;
        this.userTransformService = userTransformService;
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
                    
    @Override
    public User storeUserProfile(TwitterProfile userProfile) {
        User user = userTransformService.transform(userProfile);
        user = userService.storeUserProcess(user);
        return user;
    }

    @Override
    public void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch) {
        log.info("-----exampleTest-------------------------------------------");
        log.info("Hello, Testing-World.");
        log.info("We are waiting for fetchTweetsFromTwitterSearch.");
        log.info("number of tweets: " + tweetService.count());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info("number of tweets: " + tweetService.count());
            if (!fetchTestData) {
                for (long id : idTwitterToFetch) {
                    try {
                        org.springframework.social.twitter.api.Tweet twitterTweet = twitterApiService.findOneTweetById(id);
                        this.storeOneTweet(twitterTweet);
                    } catch (TwitterApiException e){
                        log.error("twitterApiService.findOneTweetById: " + e.getMessage());
                    }
                }
            }
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.info("number of tweets: " + tweetService.count());
        log.info("------------------------------------------------");
    }
    
}
