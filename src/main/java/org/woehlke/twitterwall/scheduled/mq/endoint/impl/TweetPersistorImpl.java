package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.TweetPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
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
    public Message<TweetFromTwitter> persistTweet(Message<TweetFromTwitter> incomingUserMessage) {
        TweetFromTwitter receivedMessage = incomingUserMessage.getPayload();
        long id = receivedMessage.getTaskId();
        Task task = taskService.findById(id);
        Tweet tweet = storeOneTweetPerform.storeOneTweetPerform(receivedMessage.getTweet(),task);
        TweetFromTwitter newTweetMsg = new TweetFromTwitter(id,tweet,receivedMessage.getTweetFromTwitter(),true,true);
        Message<TweetFromTwitter> mqMessageOut = MessageBuilder.withPayload(newTweetMsg)
            .copyHeaders(incomingUserMessage.getHeaders())
            .setHeader("persisted",Boolean.TRUE)
            .build();
        return mqMessageOut;
    }
}
