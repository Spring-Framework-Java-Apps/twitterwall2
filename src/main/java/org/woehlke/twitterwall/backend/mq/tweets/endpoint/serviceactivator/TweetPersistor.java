package org.woehlke.twitterwall.backend.mq.tweets.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;

public interface TweetPersistor {

    Message<TweetMessage> persistTweet(Message<TweetMessage> incomingUserMessage);
}
