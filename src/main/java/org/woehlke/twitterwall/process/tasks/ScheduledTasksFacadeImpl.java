package org.woehlke.twitterwall.process.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;
import org.woehlke.twitterwall.process.parts.TwitterApiService;
import org.woehlke.twitterwall.process.parts.UrlApiService;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class ScheduledTasksFacadeImpl implements ScheduledTasksFacade {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Value("${twitterwall.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Autowired
    public ScheduledTasksFacadeImpl(PersistDataFromTwitter persistDataFromTwitter, TwitterApiService twitterApiService, UserService userService, UrlApiService urlApiService, UrlService urlService, TweetService tweetService) {
        this.persistDataFromTwitter = persistDataFromTwitter;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.urlApiService = urlApiService;
        this.urlService = urlService;
        this.tweetService = tweetService;
    }

    private final PersistDataFromTwitter persistDataFromTwitter;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final UrlApiService urlApiService;

    private final UrlService urlService;

    private final TweetService tweetService;

    @Override
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets from Twitter:";
        log.debug("fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        try {
            log.debug("---------------------------------------");
            int loopId = 0;
            for (Tweet tweet : twitterApiService.findTweetsForSearchQuery()) {
                loopId++;
                storeOneTweet(tweet, loopId);
            }
            log.debug("---------------------------------------");
            try {
                if (fetchTestData) {
                    for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
                        try {
                            Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                            loopId++;
                            storeOneTweet(tweet, loopId);
                        } catch (TwitterApiException ex){
                            log.warn(ex.getMessage());
                        }
                    }
                }
            } catch (RateLimitExceededException e){
                log.info(e.getMessage());
            }
            log.debug("---------------------------------------");
            try {
                loopId = 0;
                List<User> userList = userService.getAll();
                for (User user : userList) {
                    try {
                        Url url = urlService.findByUrl(user.getUrl());
                        loopId++;
                        log.debug("Id: " + loopId + "," + url.toString());
                    } catch (FindUrlByUrlException e) {
                        try {
                            Url url = urlApiService.fetchUrl(user.getUrl());
                            url = urlService.store(url);
                            loopId++;
                            log.debug("Id: " + loopId + "," + url.toString());
                        } catch (FetchUrlException ex) {
                            log.debug(ex.getMessage());
                        }
                    }
                }
            } catch (RateLimitExceededException rlee){
                log.info(rlee.getMessage());
            }
            log.debug("---------------------------------------");
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        } catch (RuntimeException e) {
            throw new TwitterApiException(msg, e);
        } catch (Exception e) {
            throw new TwitterApiException(msg, e);
        } finally {
            log.debug("---------------------------------------");
        }
    }

    private void storeOneTweet(Tweet tweet, int loopId) {
        String msg = "store One Tweet: ";
        log.debug(msg + loopId);
        log.debug(msg + "Id:    " + tweet.getId());
        log.debug(msg + "User: @" + tweet.getFromUser());
        log.debug(msg + "Text:  " + tweet.getText());
        log.debug(msg + "Image: " + tweet.getProfileImageUrl());
        try {
            this.persistDataFromTwitter.storeOneTweet(tweet);
        } catch (RuntimeException e) {
            throw new TwitterApiException(msg, e);
        } catch (Exception e) {
            throw new TwitterApiException(msg, e);
        } finally {
            log.debug("---------------------------------------");
        }
    }

    @Override
    public void updateUserProfiles() {
        String msg = "update User Profiles: ";
        log.debug(msg + "The time is now {}", dateFormat.format(new Date()));
        log.debug("---------------------------------------");
        try {
            List<Long> userProfileTwitterIds = userService.getAllTwitterIds();
            for (Long userProfileTwitterId : userProfileTwitterIds) {
                try {
                    TwitterProfile userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
                    User user = persistDataFromTwitter.updateUserProfile(userProfile);
                    log.debug(msg + user.toString());
                } catch (RateLimitExceededException e) {
                    throw new TwitterApiException(msg+userProfileTwitterId, e);
                } catch (TwitterApiException e) {
                    log.info(msg+userProfileTwitterId + e.getMessage());
                } finally {
                    log.debug("---------------------------------------");
                }
            }
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + "updateUserProfiles: check your Network Connection!", e);
        } catch (RateLimitExceededException e) {
            throw new TwitterApiException(msg, e);
        } catch (RuntimeException e) {
            throw new TwitterApiException("updateUserProfiles", e);
        } catch (Exception e) {
            throw new TwitterApiException("updateUserProfiles", e);
        } finally {
            log.debug("---------------------------------------");
        }
    }

    @Override
    public void updateTweets() {
        String msg = "update Tweets: ";
        log.debug(msg + "The time is now {}", dateFormat.format(new Date()));
        try {
            int loopId = 0;
            List<Long> tweetTwitterIds = tweetService.getAllTwitterIds();
            for (Long tweetTwitterId : tweetTwitterIds) {
                try {
                    Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                    loopId++;
                    storeOneTweet(tweet, loopId);
                    Thread.sleep(millisToWaitForFetchTweetsFromTwitterSearch);
                } catch (RateLimitExceededException e) {
                    throw new TwitterApiException(msg+tweetTwitterId, e);
                } catch (InterruptedException ex){
                    log.info(msg+tweetTwitterId + ex.getMessage());
                } catch (TwitterApiException e) {
                    log.info(msg+tweetTwitterId + e.getMessage());
                } finally {
                    log.debug("---------------------------------------");
                }
            }
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
}
