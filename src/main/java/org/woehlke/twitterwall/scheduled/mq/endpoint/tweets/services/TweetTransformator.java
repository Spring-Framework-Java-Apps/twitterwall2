package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

public interface TweetTransformator {

    Message<TweetMessage> transformTweet(Message<TweetMessage> message);
}
