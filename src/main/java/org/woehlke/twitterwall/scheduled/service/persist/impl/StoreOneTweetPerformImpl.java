package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskInfo;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.service.persist.*;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreOneTweetPerformImpl implements StoreOneTweetPerform {

    /** Method because of recursive Method Call in this Method **/
    public Tweet storeOneTweetPerform(Tweet tweet, Task task){
        String msg = "storeOneTweetPerform( idTwitter="+tweet.getIdTwitter()+" ) ";
        /** Retweeted Tweet */
        Tweet retweetedStatus = tweet.getRetweetedStatus();
        if (retweetedStatus != null) {
            /** Method because of recursive Method Call in this Method **/
            retweetedStatus = this.storeOneTweetPerform(retweetedStatus, task);
            tweet.setRetweetedStatus(retweetedStatus);
        }
        /** TaskInfo */
        TaskInfo taskInfo = tweet.getTaskInfo();
        taskInfo = taskInfo.setTaskInfoFromTask(task);
        tweet.setTaskInfo(taskInfo);
        /** User */
        User user = tweet.getUser();
        user = storeUserProcess.storeUserProcess(user,task);
        tweet.setUser(user);
        /** Entities */
        Entities entities = tweet.getEntities();
        entities = storeEntitiesProcess.storeEntitiesProcess(entities,task);
        tweet.setEntities(entities);
        /** Tweet itself */
        tweet = tweetService.store(tweet,task);
        log.debug(msg+"tweetService.store: "+tweet.toString());
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
