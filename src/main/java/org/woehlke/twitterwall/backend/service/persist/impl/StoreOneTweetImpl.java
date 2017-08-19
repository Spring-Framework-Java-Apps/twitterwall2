package org.woehlke.twitterwall.backend.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.backend.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.backend.service.persist.StoreOneTweetPerform;
import org.woehlke.twitterwall.backend.service.transform.TweetTransformService;

/**
 * Created by tw on 09.07.17.
 */

@Component
public class StoreOneTweetImpl implements StoreOneTweet {

    @Override
    public Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet tweetSource, Task task) {
        String msg = "storeOneTweet. tweetSource.getId= "+tweetSource.getId() +" task: "+task.getUniqueId()+" : ";
        try {
            Tweet tweetTarget = tweetTransformService.transform(tweetSource, task);
            tweetTarget = storeOneTweetPerform.storeOneTweetPerform(tweetTarget, task);
            return tweetTarget;
        } catch (Exception e){
            log.error(msg+e.getMessage());
            return null;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(StoreOneTweetImpl.class);

    private final TweetTransformService tweetTransformService;

    private final StoreOneTweetPerform storeOneTweetPerform;

    @Autowired
    public StoreOneTweetImpl(TweetTransformService tweetTransformService, StoreOneTweetPerform storeOneTweetPerform) {
        this.tweetTransformService = tweetTransformService;
        this.storeOneTweetPerform = storeOneTweetPerform;
    }
}
