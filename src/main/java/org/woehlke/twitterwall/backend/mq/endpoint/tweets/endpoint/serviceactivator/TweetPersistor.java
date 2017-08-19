package org.woehlke.twitterwall.backend.mq.endpoint.tweets.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetMessage;

public interface TweetPersistor {

    Message<TweetMessage> persistTweet(Message<TweetMessage> incomingUserMessage);
}
