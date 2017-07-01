package org.woehlke.twitterwall.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitterImpl;

import javax.persistence.NoResultException;

/**
 * Created by tw on 01.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class PersistDataFromTwitterTestImpl implements PersistDataFromTwitterTest {

    private static final Logger log = LoggerFactory.getLogger(PersistDataFromTwitterImpl.class);

    private final PersistDataFromTwitter persistDataFromTwitter;

    private final TwitterApiService twitterApiService;

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public PersistDataFromTwitterTestImpl(PersistDataFromTwitter persistDataFromTwitter, TwitterApiService twitterApiService) {
        this.persistDataFromTwitter = persistDataFromTwitter;
        this.twitterApiService = twitterApiService;
    }

    @Override
    public void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch) {
        String msg = "fetchTweetsFromTwitterSearchTest : ";
        log.info(msg + "-----exampleTest-------------------------------------------");
        log.info(msg + "Hello, Testing-World.");
        log.info(msg + "We are waiting for fetchTweetsFromTwitterSearch.");
        log.info(msg + "number of tweets: " + persistDataFromTwitter.countTweets());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info(msg + "number of tweets: " + persistDataFromTwitter.countTweets());
            for (long id : idTwitterToFetch) {
                try {
                    org.springframework.social.twitter.api.Tweet twitterTweet = twitterApiService.findOneTweetById(id);
                    persistDataFromTwitter.storeOneTweet(twitterTweet);
                } catch (TwitterApiException e){
                    log.error(msg + "twitterApiService.findOneTweetById: " + e.getMessage());
                } catch (EmptyResultDataAccessException ex){
                    log.error(msg + "storeOneTweet: "+ex.getMessage());
                } catch (NoResultException exe){
                    log.error(msg + "storeOneTweet: "+exe.getMessage());
                }
            }
        } catch (InterruptedException e) {
            log.warn(msg+e.getMessage());
        }
        log.info(msg + "number of tweets: " + persistDataFromTwitter.countTweets());
        log.info(msg + "------------------------------------------------");
    }

    @Override
    public void fetchUserFromTwitterSearchTest(long[] idTwitterToFetchForProfileControllerTest) {
        String msg = "fetchUserFromTwitterSearchTest : ";
        log.info(msg + "-----exampleTest-------------------------------------------");
        log.info(msg + "Hello, Testing-World.");
        log.info(msg + "number of users: " + persistDataFromTwitter.countUsers());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info(msg + "number of users: " + persistDataFromTwitter.countUsers());
            for (long id : idTwitterToFetchForProfileControllerTest) {
                TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(id);
                persistDataFromTwitter.storeUserProfile(twitterProfile);
            }
        } catch (InterruptedException e) {
            log.warn(msg+e.getMessage());
        }
        log.info(msg + "number of users: " + persistDataFromTwitter.countUsers());
        log.info(msg + "------------------------------------------------");
    }
}
