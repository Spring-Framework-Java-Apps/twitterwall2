package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.services.TweetPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweetPerform;

@Component("mqTweetPersistor")
public class TweetPersistorImpl implements TweetPersistor {

    private final TaskService taskService;

    private final StoreOneTweetPerform storeOneTweetPerform;

    @Autowired
    public TweetPersistorImpl(TaskService taskService, StoreOneTweetPerform storeOneTweetPerform) {
        this.taskService = taskService;
        this.storeOneTweetPerform = storeOneTweetPerform;
    }

    @Override
    public Message<TweetMessage> persistTweet(Message<TweetMessage> incomingUserMessage) {
        TweetMessage receivedMessage = incomingUserMessage.getPayload();
        long taskId = receivedMessage.getTaskMessage().getTaskId();
        Task task = taskService.findById(taskId);
        TweetMessage newTweetMsg = null;
        if(receivedMessage.isIgnoreTransformation()){
            newTweetMsg = receivedMessage;
        } else {
            Tweet tweet = storeOneTweetPerform.storeOneTweetPerform(receivedMessage.getTweet(),task);
            newTweetMsg = new TweetMessage(receivedMessage.getTaskMessage(),tweet,receivedMessage.getTweetFromTwitter());
        }
        Message<TweetMessage> mqMessageOut = MessageBuilder.withPayload(newTweetMsg)
            .copyHeaders(incomingUserMessage.getHeaders())
            .setHeader("persisted",Boolean.TRUE)
            .build();
        return mqMessageOut;
    }
}
