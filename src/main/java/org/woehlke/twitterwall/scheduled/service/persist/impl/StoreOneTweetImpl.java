package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweetPerform;
import org.woehlke.twitterwall.scheduled.service.transform.TweetTransformService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreOneTweetImpl implements StoreOneTweet {


    @Override
    public Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet tweetSource, Task task) {
        Tweet tweetTarget = tweetTransformService.transform(tweetSource);
        tweetTarget = storeOneTweetPerform.storeOneTweetPerform(tweetTarget,task);
        return tweetTarget;
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
