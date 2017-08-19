package org.woehlke.twitterwall.backend.mq.endpoint.tweets.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.endpoint.serviceactivator.TweetPersistor;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetMessageBuilder;
import org.woehlke.twitterwall.backend.service.persist.StoreOneTweetPerform;

@Component("mqTweetPersistor")
public class TweetPersistorImpl implements TweetPersistor {

    private final TaskService taskService;

    private final StoreOneTweetPerform storeOneTweetPerform;

    private final TweetMessageBuilder tweetMessageBuilder;

    @Autowired
    public TweetPersistorImpl(TaskService taskService, StoreOneTweetPerform storeOneTweetPerform, TweetMessageBuilder tweetMessageBuilder) {
        this.taskService = taskService;
        this.storeOneTweetPerform = storeOneTweetPerform;
        this.tweetMessageBuilder = tweetMessageBuilder;
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
