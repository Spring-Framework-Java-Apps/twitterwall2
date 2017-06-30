package org.woehlke.twitterwall.process.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;
import org.woehlke.twitterwall.backend.TwitterApiService;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ScheduledTasksFacadeImpl implements ScheduledTasksFacade,ScheduledTasksFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Value("${twitterwall.backend.twitter.fetchTestData}")
    //private boolean fetchTestData;

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public ScheduledTasksFacadeImpl(PersistDataFromTwitter persistDataFromTwitter, TwitterApiService twitterApiService, UserService userService, UrlService urlService, TweetService tweetService) {
        this.persistDataFromTwitter = persistDataFromTwitter;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.urlService = urlService;
        this.tweetService = tweetService;
    }

    private final PersistDataFromTwitter persistDataFromTwitter;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final UrlService urlService;

    private final TweetService tweetService;

    @Override
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets from Twitter: ";
        log.info("fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        try {
            log.info("---------------------------------------");
            int loopId = 0;
            for (Tweet tweet : twitterApiService.findTweetsForSearchQuery()) {
                loopId++;
                log.info(msg+loopId);
                try {
                    this.persistDataFromTwitter.storeOneTweet(tweet);
                } catch (EmptyResultDataAccessException e)  {
                    log.warn(e.getMessage());
                }catch (NoResultException e){
                    log.warn(e.getMessage());
                } catch (Exception e){
                    log.warn(e.getMessage());
                }
            }
            log.info("---------------------------------------");

            /*
            try {
                if (fetchTestData) {
                    for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
                        try {
                            Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                            loopId++;
                            log.info(msg+loopId);
                            this.persistDataFromTwitter.storeOneTweet(tweet);
                        } catch (EmptyResultDataAccessException e)  {
                            log.warn(e.getMessage());
                        } catch (TwitterApiException ex){
                            log.warn(ex.getMessage());
                        } catch (NoResultException e){
                            log.warn(e.getMessage());
                        }
                    }
                }
            } catch (RateLimitExceededException e){
                log.info(e.getMessage());
            } catch (Exception e){
                log.warn(e.getMessage());
            }
            */
            log.info("---------------------------------------");
            /*
            try {
                loopId = 0;
                List<User> userList = userService.getAll();
                for (User user : userList) {
                    String urlstr = user.getUrl();
                    if (urlstr != null){
                        log.info(urlstr);
                        try {
                            Url url = urlService.findByUrl(urlstr);
                            loopId++;
                            log.info("Id: " + loopId + "," + url.toString());
                        } catch (FindUrlByUrlException e) {
                            try {
                                Url url = urlService.getPersistentUrlFor(urlstr);
                                loopId++;
                                log.info("Id: " + loopId + "," + url.toString());
                            } catch (FetchUrlException ex) {
                                log.info(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (RateLimitExceededException rlee){
                log.info(rlee.getMessage());
            }
            log.info("---------------------------------------");
            */
        } catch (ResourceAccessException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException(msg + " check your Network Connection!", e);
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException(msg, e);
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException(msg, e);
        } finally {
            log.info("---------------------------------------");
        }
    }

    @Override
    public void updateUserProfiles() {
        String msg = "update User Profiles: ";
        log.info(msg + "The time is now {}", dateFormat.format(new Date()));
        log.info("---------------------------------------");
        try {
            List<Long> userProfileTwitterIds = userService.getAllTwitterIds();
            for (Long userProfileTwitterId : userProfileTwitterIds) {
                try {
                    TwitterProfile userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
                    User user = persistDataFromTwitter.storeUserProfile(userProfile);
                    log.info(msg + user.toString());
                } catch (RateLimitExceededException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    throw new TwitterApiException(msg+userProfileTwitterId, e);
                } catch (TwitterApiException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    log.info(msg+userProfileTwitterId + e.getMessage());
                } finally {
                    log.info("---------------------------------------");
                }
            }
        } catch (ResourceAccessException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            //throw new TwitterApiException(msg + "updateUserProfiles: check your Network Connection!", e);
            log.error(msg + " check your Network Connection!");
            throw e;
        } catch (RateLimitExceededException e) {
            //throw new TwitterApiException(msg, e);
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException("updateUserProfiles", e);
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException("updateUserProfiles", e);
        } finally {
            log.info("---------------------------------------");
        }
    }

    @Override
    public void updateTweets() {
        String msg = "update Tweets: ";
        log.info(msg + "The time is now {}", dateFormat.format(new Date()));
        try {
            int loopId = 0;
            List<Long> tweetTwitterIds = tweetService.getAllTwitterIds();
            for (Long tweetTwitterId : tweetTwitterIds) {
                try {
                    Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                    loopId++;
                    log.info(""+loopId);
                    this.persistDataFromTwitter.storeOneTweet(tweet);
                    Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
                } catch (RateLimitExceededException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    throw new TwitterApiException(msg+tweetTwitterId, e);
                } catch (InterruptedException ex){
                    log.warn(msg + ex.getMessage());
                    Throwable t = ex.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    log.info(msg+tweetTwitterId + ex.getMessage());
                } catch (TwitterApiException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    log.info(msg+tweetTwitterId + e.getMessage());
                } finally {
                    log.info("---------------------------------------");
                }
            }
        } catch (ResourceAccessException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            log.error(msg + " check your Network Connection!");
            throw e;
            //throw new TwitterApiException(, e);
        } catch (RateLimitExceededException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException(msg, e);
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
           // throw new TwitterApiException(msg, e);
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
            //throw new TwitterApiException(msg, e);
        } finally {
            log.info("---------------------------------------");
        }
    }
}
