package org.woehlke.twitterwall.helper;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.exceptions.FindTweetByIdTwitterException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.process.StoreTweetsProcess;
import org.woehlke.twitterwall.process.TwitterApiService;

import javax.transaction.Transactional;

/**
 * Created by tw on 20.06.17.
 */
@Service
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class TestHelperServiceImpl implements TestHelperService {

    private final TweetService tweetService;
    private final TwitterApiService twitterApiService;
    private final StoreTweetsProcess storeTweetsProcess;
    
    private static final Logger log = LoggerFactory.getLogger(TestHelperServiceImpl.class);

    private final static long millisToWaitForFetchTweetsFromTwitterSearch = 20000;

    @Value("${twitterwall.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Autowired
    public TestHelperServiceImpl(TweetService tweetService, TwitterApiService twitterApiService, StoreTweetsProcess storeTweetsProcess) {
        this.tweetService = tweetService;
        this.twitterApiService = twitterApiService;
        this.storeTweetsProcess = storeTweetsProcess;
    }
    
    @Override
    public void performTweetTest(long idTwitter,String output, boolean retweet){
        log.info("idTwitter: "+idTwitter);
        try {
            Tweet tweet = tweetService.findByIdTwitter(idTwitter);
            log.info("text:          " + tweet.getText());
            log.info("Expected:      " + output + "---");
            String formattedText;
            if (retweet) {
                formattedText = tweet.getRetweetedStatus().getFormattedText();
            } else {
                formattedText = tweet.getFormattedText();
            }
            log.info("FormattedText: " + formattedText + "---");
            Assert.assertEquals(output, formattedText);
        } catch (FindTweetByIdTwitterException e){
            log.error(e.getMessage());
        }
        log.info("------------------------------------------------");
    }

    @Override
    public void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch) {
        log.info("-----exampleTest-------------------------------------------");
        log.info("Hello, Testing-World.");
        log.info("We are waiting for fetchTweetsFromTwitterSearch.");
        log.info("number of tweets: "+tweetService.count());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info("number of tweets: "+tweetService.count());
            if(!fetchTestData) {
                for (long id : idTwitterToFetch) {
                    org.springframework.social.twitter.api.Tweet twitterTweet = twitterApiService.findOneTweetById(id);
                    storeTweetsProcess.storeOneTweet(twitterTweet);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("number of tweets: "+tweetService.count());
        log.info("------------------------------------------------");
    }
}
