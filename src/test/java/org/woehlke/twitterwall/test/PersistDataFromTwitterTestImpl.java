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
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;

/**
 * Created by tw on 01.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class PersistDataFromTwitterTestImpl implements PersistDataFromTwitterTest {

    private static final Logger log = LoggerFactory.getLogger(PersistDataFromTwitterTestImpl.class);


    private final TwitterApiService twitterApiService;

    private final StoreOneTweet storeOneTweet;

    private final StoreUserProfile storeUserProfile;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private long millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public PersistDataFromTwitterTestImpl(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, UserService userService, CountedEntitiesService countedEntitiesService) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch) {
        String msg = "fetchTweetsFromTwitterSearchTest : ";
        log.info(msg + "-----exampleTest-------------------------------------------");
        log.info(msg + "Hello, Testing-World.");
        log.info(msg + "We are waiting for fetchTweetsFromTwitterSearch.");
        log.info(msg + "number of tweets: " + countedEntitiesService.countTweets());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info(msg + "number of tweets: " + countedEntitiesService.countTweets());
            for (long id : idTwitterToFetch) {
                try {
                    org.springframework.social.twitter.api.Tweet twitterTweet = twitterApiService.findOneTweetById(id);
                    storeOneTweet.storeOneTweet(twitterTweet);
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
        log.info(msg + "number of tweets: " + countedEntitiesService.countTweets());
        log.info(msg + "------------------------------------------------");
    }

    @Override
    public void fetchUserFromTwitterSearchTest(long[] idTwitterToFetchForProfileControllerTest) {
        String msg = "fetchUserFromTwitterSearchTest : ";
        log.info(msg + "-----exampleTest-------------------------------------------");
        log.info(msg + "Hello, Testing-World.");
        log.info(msg + "number of users: " + countedEntitiesService.countUsers());
        try {
            Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
            log.info(msg + "number of users: " + countedEntitiesService.countUsers());
            for (long id : idTwitterToFetchForProfileControllerTest) {
                TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(id);
                storeUserProfile.storeUserProfile(twitterProfile);
            }
        } catch (InterruptedException e) {
            log.warn(msg+e.getMessage());
        }
        log.info(msg + "number of users: " + countedEntitiesService.countUsers());
        log.info(msg + "------------------------------------------------");
    }
}
