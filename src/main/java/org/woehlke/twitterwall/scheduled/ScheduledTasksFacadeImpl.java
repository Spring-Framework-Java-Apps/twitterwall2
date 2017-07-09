package org.woehlke.twitterwall.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.frontend.model.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ScheduledTasksFacadeImpl implements ScheduledTasksFacade {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Autowired
    public ScheduledTasksFacadeImpl(PersistDataFromTwitter persistDataFromTwitter, TwitterApiService twitterApiService, UserService userService, TweetService tweetService, MentionService mentionService) {
        this.persistDataFromTwitter = persistDataFromTwitter;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.mentionService = mentionService;
    }

    private final PersistDataFromTwitter persistDataFromTwitter;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final TweetService tweetService;

    private final MentionService mentionService;

    @Override
    public CountedEntities fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets from Twitter: ";
        log.info(msg+"---------------------------------------");
        log.info(msg+ "fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        log.info(msg+"---------------------------------------");
        try {
            int loopId = 0;
            for (Tweet tweet : twitterApiService.findTweetsForSearchQuery()) {
                loopId++;
                log.info(msg+loopId);
                try {
                    this.persistDataFromTwitter.storeOneTweet(tweet);
                } catch (EmptyResultDataAccessException e)  {
                    log.warn(msg+e.getMessage());
                } catch (NoResultException e){
                    log.warn(msg+e.getMessage());
                } catch (Exception e){
                    log.warn(msg+e.getMessage());
                }
            }
        } catch (ResourceAccessException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } finally {
            log.info(msg+"---------------------------------------");
        }
        return this.persistDataFromTwitter.countAll();
    }

    @Override
    public CountedEntities updateUserProfiles(){
        String msg = "update User Profiles From ProfileId: ";
        log.info(msg+"---------------------------------------");
        log.info(msg + "START - The time is now {}", dateFormat.format(new Date()));
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
                    log.info(msg +"---------------------------------------");
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
        } catch (RateLimitExceededException e) {
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } finally {
            log.info(msg +"---------------------------------------");
        }
        log.info(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.info(msg+"---------------------------------------");
        return this.persistDataFromTwitter.countAll();
    }

    @Override
    public CountedEntities updateUserProfilesFromMentions(){
        String msg = "update User Profiles from Mentions: ";
        log.info(msg + "START - The time is now {}", dateFormat.format(new Date()));
        try {
            List<Mention> allPersMentions =  mentionService.getAll();
            for(Mention onePersMentions :allPersMentions){
                String screenName = onePersMentions.getScreenName();
                if((screenName != null) && (!screenName.isEmpty())) {
                    try {
                        TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                        User user = persistDataFromTwitter.storeUserProfile(twitterProfile);
                        log.info(msg + user.toString());
                    } catch (RateLimitExceededException e) {
                        log.warn(msg + e.getMessage());
                        Throwable t = e.getCause();
                        while(t != null){
                            log.warn(msg + t.getMessage());
                            t = t.getCause();
                        }
                        throw new TwitterApiException(msg+screenName, e);
                    } catch (TwitterApiException e) {
                        log.warn(msg + e.getMessage());
                        Throwable t = e.getCause();
                        while(t != null){
                            log.warn(msg + t.getMessage());
                            t = t.getCause();
                        }
                        log.info(msg+screenName + e.getMessage());
                    } finally {
                        log.info(msg +"---------------------------------------");
                    }
                }
            }
        } catch (ResourceAccessException e) {
            log.warn(msg + " ResourceAccessException: " + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + " caused by: "+ t.getMessage());
                t = t.getCause();
            }
            log.error(msg + " check your Network Connection!");
            throw e;
        } catch (RateLimitExceededException e) {
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } finally {
            log.info(msg +"---------------------------------------");
        }
        log.info(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.info(msg+"---------------------------------------");
        return this.persistDataFromTwitter.countAll();
    }

    @Override
    public CountedEntities updateTweets() {
        String msg = "update Tweets: ";
        log.info(msg + "---------------------------------------");
        log.info(msg + "The time is now {}", dateFormat.format(new Date()));
        log.info(msg + "---------------------------------------");
        try {
            int loopId = 0;
            List<Long> tweetTwitterIds = tweetService.getAllTwitterIds();
            for (Long tweetTwitterId : tweetTwitterIds) {
                try {
                    Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                    loopId++;
                    log.info(msg + ""+loopId);
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
                    log.info(msg + "---------------------------------------");
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
        } catch (RateLimitExceededException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } finally {
            log.info(msg + "---------------------------------------");
        }
        return this.persistDataFromTwitter.countAll();
    }

    @Override
    public void fetchUsersFromDefinedUserList() {
        String msg = "update Tweets: ";
        log.info(msg + "---------------------------------------");
        log.info(msg + "The time is now {}", dateFormat.format(new Date()));
        log.info(msg + "---------------------------------------");
        List<TwitterProfile> userProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUserListName);
        for(TwitterProfile twitterProfile:userProfiles) {
            User user = persistDataFromTwitter.storeUserProfileForUserList(twitterProfile);
        }
    }
}
