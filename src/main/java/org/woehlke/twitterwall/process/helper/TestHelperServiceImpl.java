package org.woehlke.twitterwall.process.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.process.tasks.PersistDataFromTwitter;
import org.woehlke.twitterwall.process.parts.TwitterApiService;

import javax.transaction.Transactional;

/**
 * Created by tw on 20.06.17.
 */
@Service
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class TestHelperServiceImpl implements TestHelperService {

    private final TweetService tweetService;
    private final TwitterApiService twitterApiService;
    private final PersistDataFromTwitter persistDataFromTwitter;

    private static final Logger log = LoggerFactory.getLogger(TestHelperServiceImpl.class);

    @Value("${twitterwall.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Autowired
    public TestHelperServiceImpl(TweetService tweetService, TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter) {
        this.tweetService = tweetService;
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
    }

    @Override
    public String performTweetTest(long idTwitter, String output, boolean retweet) {
        String msg = "performTweetTest: ";
        log.debug("idTwitter: " + idTwitter);
        try {
            Tweet tweet = tweetService.findByIdTwitter(idTwitter);
            log.debug("text:          " + tweet.getText());
            log.debug("Expected:      " + output + "---");
            String formattedText;
            if (retweet) {
                formattedText = tweet.getRetweetedStatus().getFormattedText();
            } else {
                formattedText = tweet.getFormattedText();
            }
            log.debug("FormattedText: " + formattedText + "---");
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
            log.debug("---------------------------------------");
        }
    }

    @Override
    public void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch) {
        log.debug("-----exampleTest-------------------------------------------");
        log.debug("Hello, Testing-World.");
        log.debug("We are waiting for fetchTweetsFromTwitterSearch.");
        log.debug("number of tweets: " + tweetService.count());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.debug("number of tweets: " + tweetService.count());
            if (!fetchTestData) {
                for (long id : idTwitterToFetch) {
                    org.springframework.social.twitter.api.Tweet twitterTweet = twitterApiService.findOneTweetById(id);
                    persistDataFromTwitter.storeOneTweet(twitterTweet);
                }
            }
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.debug("number of tweets: " + tweetService.count());
        log.debug("------------------------------------------------");
    }

    @Override
    public void waitForImport() {
        log.debug("Hello, Testing-World.");
        log.debug("We are waiting for fetchTweetsFromTwitterSearch.");
        try {
            log.debug("number of tweets: " + tweetService.count());
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.debug("number of tweets: " + tweetService.count());
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.debug("number of tweets: " + tweetService.count());
        log.debug("------------------------------------------------");
    }
}
