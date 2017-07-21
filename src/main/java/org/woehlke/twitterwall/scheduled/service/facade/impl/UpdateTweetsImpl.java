package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.conf.TwitterwallBackendProperties;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateTweets;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateTweetsImpl implements UpdateTweets {

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
            boolean hasNext;
            Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
            do {
                Page<Long> tweetTwitterIds = tweetService.findAllTwitterIds(pageRequest);
                hasNext = tweetTwitterIds.hasNext();
                long number = tweetTwitterIds.getTotalElements();
                for (Long tweetTwitterId : tweetTwitterIds) {
                    loopId++;
                    allLoop++;
                    String counter = " ( " + loopId + " from " + number + " ) [ " + allLoop + " ] ";
                    try {
                        Tweet tweet = twitterApiService.findOneTweetById(tweetTwitterId);
                        log.debug(msg + "" + counter);
                        org.woehlke.twitterwall.oodm.entities.Tweet tweetPers = this.storeOneTweet.storeOneTweet(tweet, task);
                        log.debug(msg + "" + counter + tweetPers);
                        Set<Mention> mentions = tweetPers.getEntities().getMentions();
                        int subLoopId = 0;
                        int subNumber = mentions.size();
                        for (Mention mention : mentions) {
                            allLoop++;
                            subLoopId++;
                            String subCounter = counter + " ( " + subLoopId + " from " + subNumber + " ) [ " + allLoop + " ] ";
                            try {
                                log.debug(msg + subCounter);
                                User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(), task);
                                log.debug(msg + subCounter + userFromMention.toString());
                            } catch (IllegalArgumentException exe) {
                                task = taskService.warn(task, exe, msg + subCounter);
                            }
                        }
                        log.debug(msg + "-----------------------------------------------------");
                        log.debug(msg + "Start SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                        Thread.sleep(twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls());
                        log.debug(msg + "Done SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                        log.debug(msg + "-----------------------------------------------------");
                    } catch (RateLimitExceededException e) {
                        task = taskService.error(task, e, msg + counter);
                        throw e;
                    } catch (InterruptedException ex) {
                        task = taskService.warn(task, ex, msg + counter);
                    } finally {
                        log.debug(msg + "---------------------------------------");
                    }
                }
                pageRequest = pageRequest.next();
            } while (hasNext);
        } catch (ResourceAccessException e) {
            task = taskService.error(task,e,msg + " check your Network Connection!");
        } catch (RateLimitExceededException e) {
            task = taskService.error(task,e,msg);
        }
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        this.taskService.event(task,report);
        this.taskService.done(task);
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "DONE: The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
    }


    private static final Logger log = LoggerFactory.getLogger(UpdateTweetsImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final StoreOneTweet storeOneTweet;

    private final TwitterApiService twitterApiService;

    private final TweetService tweetService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    private final TwitterwallBackendProperties twitterwallBackendProperties;

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterProperties twitterProperties;

    @Autowired
    public UpdateTweetsImpl(TwitterApiService twitterApiService, TweetService tweetService, StoreOneTweet storeOneTweet, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName, TwitterwallBackendProperties twitterwallBackendProperties, TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterProperties twitterProperties) {
        this.twitterApiService = twitterApiService;
        this.tweetService = tweetService;
        this.storeOneTweet = storeOneTweet;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
        this.twitterwallBackendProperties = twitterwallBackendProperties;
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterProperties = twitterProperties;
    }

}
