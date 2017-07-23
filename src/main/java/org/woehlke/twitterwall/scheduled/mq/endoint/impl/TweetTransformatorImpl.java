package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.TweetTransformator;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMsg;
import org.woehlke.twitterwall.scheduled.service.transform.TweetTransformService;

@Component("mqTweetTransformator")
public class TweetTransformatorImpl implements TweetTransformator {

    private final TweetTransformService tweetTransformService;

    private final TaskService taskService;

    @Autowired
    public TweetTransformatorImpl(TweetTransformService tweetTransformService, TaskService taskService) {
        this.tweetTransformService = tweetTransformService;
        this.taskService = taskService;
    }

    @Override
    public Message<TweetFromTwitter> transformTweet(Message<TweetFromTwitter> mqMessageIn) {
        TweetFromTwitter inComingTweetMessage = mqMessageIn.getPayload();
        long taskId = inComingTweetMessage.getTaskId();
        Task task = taskService.findById(taskId);
        Tweet myTweet = tweetTransformService.transform(inComingTweetMessage.getTweetFromTwitter(),task);
        boolean tansformed = true;
        TweetFromTwitter retVal = new TweetFromTwitter(taskId,myTweet,inComingTweetMessage.getTweetFromTwitter(),tansformed);
        Message<TweetFromTwitter> mqMessageOut = MessageBuilder.withPayload(retVal)
            .copyHeaders(mqMessageIn.getHeaders())
            .build();
        return mqMessageOut;
    }
}
