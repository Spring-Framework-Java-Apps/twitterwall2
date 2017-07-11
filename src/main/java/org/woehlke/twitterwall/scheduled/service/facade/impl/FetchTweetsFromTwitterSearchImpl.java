package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.woehlke.twitterwall.scheduled.service.facade.FetchTweetsFromTwitterSearch;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class FetchTweetsFromTwitterSearchImpl implements FetchTweetsFromTwitterSearch {

    private static final Logger log = LoggerFactory.getLogger(FetchTweetsFromTwitterSearchImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    private final TwitterApiService twitterApiService;

    private final StoreOneTweet storeOneTweet;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public FetchTweetsFromTwitterSearchImpl(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
    }

    @Override
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets from Twitter: ";
        log.debug(msg+"---------------------------------------");
        log.debug(msg+ "START fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
        Task task = taskService.create(msg, TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH);
        try {
            int loopId = 0;
            for (Tweet tweet : twitterApiService.findTweetsForSearchQuery()) {
                loopId++;
                log.debug(msg+loopId);
                try {
                    org.woehlke.twitterwall.oodm.entities.Tweet tweetPers = this.storeOneTweet.storeOneTweet(tweet,task);
                    for(Mention mention:tweetPers.getMentions()){
                        try {
                            User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(),task);
                            log.debug(msg+userFromMention.toString());
                        } catch (IllegalArgumentException exe){
                            log.debug(msg+exe.getMessage());
                        }
                    }
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
            task = taskService.error(task,e);
            //throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
            //throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            task = taskService.error(task,e);
            //throw e;
        }
        taskService.done(task);
        log.debug(msg+"---------------------------------------");
        log.debug(msg+ "DONE fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }
}
