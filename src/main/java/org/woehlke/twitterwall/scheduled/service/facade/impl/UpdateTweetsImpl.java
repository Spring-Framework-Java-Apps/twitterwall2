package org.woehlke.twitterwall.scheduled.service.facade.impl;

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
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskType;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateTweets;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateTweetsImpl implements UpdateTweets {

    private static final Logger log = LoggerFactory.getLogger(UpdateTweetsImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitBetweenTwoApiCalls}")
    private int millisToWaitBetweenTwoApiCalls;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;


    private final StoreOneTweet storeOneTweet;

    private final TwitterApiService twitterApiService;

    private final TweetService tweetService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public UpdateTweetsImpl(TwitterApiService twitterApiService, TweetService tweetService, StoreOneTweet storeOneTweet, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName) {
        this.twitterApiService = twitterApiService;
        this.tweetService = tweetService;
        this.storeOneTweet = storeOneTweet;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
    }

    @Override
    public void updateTweets() {
        String msg = "update Tweets: ";
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "START: The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
        Task task = taskService.create(msg, TaskType.UPDATE_TWEETS);
        int loopId = 0;
        int allLoop = 0;
        try {
            List<Long> tweetTwitterIds = tweetService.getAllTwitterIds();
            int number = tweetTwitterIds.size();
            for (Long tweetTwitterId : tweetTwitterIds) {
                loopId++;
                allLoop++;
                String counter = " ( "+loopId+ " from "+number+" ) ["+allLoop+"] ";
                try {
                    Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                    log.debug(msg + ""+counter);
                    org.woehlke.twitterwall.oodm.entities.Tweet tweetPers = this.storeOneTweet.storeOneTweet(tweet, task);
                    log.debug(msg + ""+counter+tweetPers);
                    Set<Mention> mentions = tweetPers.getEntities().getMentions();
                    int subLoopId = 0;
                    int subNumber = mentions.size();
                    for(Mention mention:mentions){
                        allLoop++;
                        subLoopId++;
                        String subCounter = counter+" ( "+subLoopId+ " from "+subNumber+" ) ["+allLoop+"] ";
                        try {
                            log.debug(msg+subCounter);
                            User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(),task);
                            log.debug(msg+subCounter+userFromMention.toString());
                        } catch (IllegalArgumentException exe){
                            log.debug(msg+subCounter+exe.getMessage());
                        }
                    }
                    log.debug(msg + "-----------------------------------------------------");
                    log.debug(msg + "Start SLEEP for "+millisToWaitBetweenTwoApiCalls+" ms "+counter);
                    Thread.sleep(millisToWaitBetweenTwoApiCalls);
                    log.debug(msg + "Done SLEEP for "+millisToWaitBetweenTwoApiCalls+" ms "+counter);
                    log.debug(msg + "-----------------------------------------------------");
                } catch (RateLimitExceededException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    throw e;
                } catch (InterruptedException ex){
                    log.warn(msg + ex.getMessage());
                    Throwable t = ex.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    log.debug(msg+tweetTwitterId + ex.getMessage());
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
        } catch (RateLimitExceededException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
        }
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        task.event(report);
        this.taskService.done(task);
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "DONE: The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
    }
}