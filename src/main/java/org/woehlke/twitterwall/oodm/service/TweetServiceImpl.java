package org.woehlke.twitterwall.oodm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService, TweetApiServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;
    
    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet create(Tweet myTweet) {
        return tweetRepository.persist(myTweet);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet update(Tweet myTweet) {
        return tweetRepository.update(myTweet);
    }

    @Override
    public List<Tweet> getAll() {
        return tweetRepository.getAll(Tweet.class);
    }

    @Override
    public List<Tweet> getLatestTweets() {
        return tweetRepository.getLatestTweets();
    }

    private final static String MSG = "hashtagText is not valid";

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("getTweetsForHashTag: "+MSG);
        }
        return tweetRepository.getTweetsForHashTag(hashtagText);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("countTweetsForHashTag: "+MSG);
        }
        return tweetRepository.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetRepository.count(Tweet.class);
    }

    /*
    @Override
    public List<Tweet> getTestTweetsForTweetTest() {
        List<Tweet> list = new ArrayList<>();
        for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
            Tweet tweet = this.findByIdTwitter(idTwitter);
            list.add(tweet);
        }
        return list;
    }
    */

    @Override
    public List<Tweet> getTweetsForUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("getTweetsForUser: user is null");
        }
        return tweetRepository.getTweetsForUser(user);
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return tweetRepository.getAllTwitterIds();
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter,Tweet.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet store(Tweet tweet) {
        try {
            Tweet tweetPersistent = tweetRepository.findByIdTwitter(tweet.getIdTwitter(),Tweet.class);
            tweet.setId(tweetPersistent.getId());
            return tweetRepository.update(tweet);
        } catch (EmptyResultDataAccessException e) {
            return tweetRepository.persist(tweet);
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
            } catch (EmptyResultDataAccessException e) {
                log.warn(msg + e.getMessage());
                throw e;
            } catch (NoResultException e) {
                log.warn(msg + e.getMessage());
                throw e;
            } catch (ResourceAccessException e) {
                log.error(msg + " check your Network Connection!", e);
                throw e;
            } catch (RateLimitExceededException e) {
                log.error(msg + e.getMessage());
                throw e;
            } catch (RuntimeException e) {
                log.error(msg + e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                throw e;
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
