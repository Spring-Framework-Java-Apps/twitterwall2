package org.woehlke.twitterwall.oodm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindTweetByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;
import org.woehlke.twitterwall.oodm.service.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.woehlke.twitterwall.process.tasks.ScheduledTasksFacadeTest.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService, TweetApiServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;

    private final UserService userService;

    private final UrlService urlService;

    private final HashTagService hashTagService;

    private final MentionService mentionService;

    private final MediaService mediaService;

    private final TickerSymbolService tickerSymbolService;

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.backend.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserService userService, UrlService urlService, HashTagService hashTagService, MentionService mentionService, MediaService mediaService, TickerSymbolService tickerSymbolService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
        this.urlService = urlService;
        this.hashTagService = hashTagService;
        this.mentionService = mentionService;
        this.mediaService = mediaService;
        this.tickerSymbolService = tickerSymbolService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet persist(Tweet myTweet) {
        return tweetRepository.persist(myTweet);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet update(Tweet myTweet) {
        return tweetRepository.update(myTweet);
    }

    @Override
    public List<Tweet> getLatestTweets() {
        return tweetRepository.getLatestTweets();
    }

    private final static String MSG = "hashtagText is not valid";

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new OodmException("getTweetsForHashTag: "+MSG);
        }
        return tweetRepository.getTweetsForHashTag(hashtagText);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new OodmException("countTweetsForHashTag: "+MSG);
        }
        return tweetRepository.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetRepository.count();
    }

    @Override
    public List<Tweet> getTestTweetsForTweetTest() {
        List<Tweet> list = new ArrayList<>();
        for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
            Tweet tweet = this.findByIdTwitter(idTwitter);
            list.add(tweet);
        }
        return list;
    }

    @Override
    public List<Tweet> getTweetsForUser(User user) {
        if(user == null){
            throw new OodmException("getTweetsForUser: user is null");
        }
        return tweetRepository.getTweetsForUser(user);
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return tweetRepository.getAllTwitterIds();
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet store(Tweet tweet) {
        try {
            Tweet tweetPersistent = tweetRepository.findByIdTwitter(tweet.getIdTwitter());
            tweet.setId(tweetPersistent.getId());
            return tweetRepository.update(tweet);
        } catch (FindTweetByIdTwitterException e) {
            return tweetRepository.persist(tweet);
        }
    }

    @Override
    public Tweet transformTweet(org.springframework.social.twitter.api.Tweet tweet) {
        if (tweet == null) { return null; } else {
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
            /* transform user */
            User user = userService.transform(twitterProfile);
            myTweet.setUser(user);
            /* transformTwitterEntities */
            Set<Url> urls = urlService.transformUrls(tweet.getEntities().getUrls());
            Set<HashTag> tags = hashTagService.transformTwitterEntitiesHashTags(tweet.getEntities().getHashTags());
            Set<Mention> mentions = mentionService.transformTwitterEntitiesMentions(tweet.getEntities().getMentions());
            Set<Media> media = mediaService.transformTwitterEntitiesMedia(tweet.getEntities().getMedia());
            Set<TickerSymbol> tickerSymbols = tickerSymbolService.transformTwitterEntitiesTickerSymbols(tweet.getEntities().getTickerSymbols());
            myTweet.setUrls(urls);
            myTweet.setTags(tags);
            myTweet.setMentions(mentions);
            myTweet.setMedia(media);
            myTweet.setTickerSymbols(tickerSymbols);
            return myTweet;
        }
    }

    @Override
    public String performTweetTest(long idTwitter, String output, boolean retweet) {
        String msg = "performTweetTest: ";
        log.info("idTwitter: " + idTwitter);
        try {
            Tweet tweet = this.findByIdTwitter(idTwitter);
            log.info("text:          " + tweet.getText());
            log.info("Expected:      " + output + "---");
            String formattedText;
            if (retweet) {
                formattedText = tweet.getRetweetedStatus().getFormattedText();
            } else {
                formattedText = tweet.getFormattedText();
            }
            log.info("FormattedText: " + formattedText + "---");
            return formattedText;
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        } catch (RateLimitExceededException e) {
            throw new TwitterApiException(msg, e);
        } catch (RuntimeException e) {
            throw new TwitterApiException(msg, e);
        } catch (Exception e) {
            throw new TwitterApiException(msg, e);
        } finally {
            log.info("---------------------------------------");
        }
    }

    @Override
    public void waitForImport() {
        log.info("Hello, Testing-World.");
        log.info("We are waiting for fetchTweetsFromTwitterSearch.");
        try {
            log.info("number of tweets: " + this.count());
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info("number of tweets: " + this.count());
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.info("number of tweets: " + this.count());
        log.info("------------------------------------------------");
    }
}
