package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.entities.Entities;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.service.persist.*;

/**
 * Created by tw on 09.07.17.
 */
@Component
public class StoreOneTweetPerformImpl implements StoreOneTweetPerform {

    /** Method because of recursive Method Call in this Method **/
    public Tweet storeOneTweetPerform(Tweet tweet, Task task){
        String msg = "storeOneTweetPerform( idTwitter=" + tweet.getUniqueId() + " ) "+task.getUniqueId() + " : ";
        try {
            /** Retweeted Tweet */
            Tweet retweetedStatus = tweet.getRetweetedStatus();
            if (retweetedStatus != null) {
                /** Method because of recursive Method Call in this Method **/
                retweetedStatus = this.storeOneTweetPerform(retweetedStatus, task);
                tweet.setRetweetedStatus(retweetedStatus);
            }
            /** Entities */
            Entities entities = tweet.getEntities();
            entities = storeEntitiesProcess.storeEntitiesProcess(entities, task);
            tweet.setEntities(entities);
            /** User */
            User user = tweet.getUser();
            user = storeUserProcess.storeUserProcess(user, task);
            tweet.setUser(user);
            /** Tweet itself */
            tweet = tweetService.store(tweet, task);
            log.debug(msg + "tweetService.store: " + tweet.getUniqueId());
        } catch (Exception e){
            log.error(msg+e.getMessage());
        }
        return tweet;
    }

    private static final Logger log = LoggerFactory.getLogger(StoreOneTweetPerformImpl.class);

    private final TweetService tweetService;

    private final StoreUserProcess storeUserProcess;

    private final StoreEntitiesProcess storeEntitiesProcess;

    @Autowired
    public StoreOneTweetPerformImpl(TweetService tweetService, StoreUserProcess storeUserProcess, StoreEntitiesProcess storeEntitiesProcess) {
        this.tweetService = tweetService;
        this.storeUserProcess = storeUserProcess;
        this.storeEntitiesProcess = storeEntitiesProcess;
    }

}
