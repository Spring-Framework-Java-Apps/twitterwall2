package org.woehlke.twitterwall.backend.mq.endpoint.tweets.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TweetMessage;

public interface TweetPersistor {

    Message<TweetMessage> persistTweet(Message<TweetMessage> incomingUserMessage);
}
