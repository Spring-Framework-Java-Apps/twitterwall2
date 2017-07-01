package org.woehlke.twitterwall.test;

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
import org.woehlke.twitterwall.oodm.service.TweetService;

import javax.persistence.NoResultException;

/**
 * Created by tw on 01.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TweetServiceTestImpl implements TweetServiceTest {
    
    private final TweetService tweetService;

    private static final Logger log = LoggerFactory.getLogger(TweetServiceTest.class);
    
    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public TweetServiceTestImpl(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public String performTweetTest(long idTwitter, String output, boolean retweet) {
        String msg = "performTweetTest: ";
        log.info(msg+ "---------------------------------------");
        log.info(msg+ "idTwitter: " + idTwitter);
        try {
            Tweet tweet = tweetService.findByIdTwitter(idTwitter);
            log.info(msg+ "text:          " + tweet.getText());
            log.info(msg+ "Expected:      " + output + "---");
            String formattedText;
            if (retweet) {
                formattedText = tweet.getRetweetedStatus().getFormattedText();
            } else {
                formattedText = tweet.getFormattedText();
            }
            log.info(msg+ "FormattedText: " + formattedText + "---");
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
            log.info(msg+ "---------------------------------------");
        }
    }

    @Override
    public void waitForImport() {
        String msg = "waitForImport: ";
        log.info(msg+"------------------------------------------------");
        log.info(msg+"Hello, Testing-World.");
        long seconds =  millisToWaitForFetchTweetsFromTwitterSearch / 1000;
        log.info(msg+"We are waiting for fetchTweetsFromTwitterSearch for seconds: "+seconds);
        try {
            log.info(msg+"number of tweets: " + tweetService.count());
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info(msg+"number of tweets: " + tweetService.count());
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        log.info(msg+"number of tweets: " + tweetService.count());
        log.info(msg+"------------------------------------------------");
    }
}
