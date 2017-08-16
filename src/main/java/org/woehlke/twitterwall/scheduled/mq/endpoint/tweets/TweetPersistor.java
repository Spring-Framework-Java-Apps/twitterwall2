package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

public interface TweetPersistor {

    Message<TweetMessage> persistTweet(Message<TweetMessage> incomingUserMessage);
}
