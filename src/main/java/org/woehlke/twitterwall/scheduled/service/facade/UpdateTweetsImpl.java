package org.woehlke.twitterwall.scheduled.service.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskType;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateTweetsImpl implements UpdateTweets {

    private static final Logger log = LoggerFactory.getLogger(UpdateTweetsImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;


    private final StoreOneTweet storeOneTweet;

    private final TwitterApiService twitterApiService;

    private final TweetService tweetService;

    private final CountedEntitiesService countedEntitiesService;

    private final TaskService taskService;

    @Autowired
    public UpdateTweetsImpl(TwitterApiService twitterApiService, TweetService tweetService, MentionService mentionService, StoreOneTweet storeOneTweet, CountedEntitiesService countedEntitiesService, TaskService taskService) {
        this.twitterApiService = twitterApiService;
        this.tweetService = tweetService;
        this.storeOneTweet = storeOneTweet;
        this.countedEntitiesService = countedEntitiesService;
        this.taskService = taskService;
    }

    @Override
    public void updateTweets() {
        String msg = "update Tweets: ";
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
        Task task = taskService.create(msg, TaskType.UPDATE_TWEETS);
        try {
            int loopId = 0;
            List<Long> tweetTwitterIds = tweetService.getAllTwitterIds();
            for (Long tweetTwitterId : tweetTwitterIds) {
                try {
                    Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                    loopId++;
                    log.debug(msg + ""+loopId);
                    this.storeOneTweet.storeOneTweet(tweet);
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
                    log.debug(msg+tweetTwitterId + ex.getMessage());
                } catch (TwitterApiException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    log.debug(msg+tweetTwitterId + e.getMessage());
                } finally {
                    log.debug(msg + "---------------------------------------");
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
            task = taskService.error(task,e);
            throw e;
        } catch (RateLimitExceededException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
            throw e;
        } finally {
            log.debug(msg + "---------------------------------------");
            this.taskService.done(task);
        }
    }
}
