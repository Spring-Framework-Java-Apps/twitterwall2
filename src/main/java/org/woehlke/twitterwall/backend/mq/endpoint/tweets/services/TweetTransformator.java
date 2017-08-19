package org.woehlke.twitterwall.backend.mq.endpoint.tweets.services;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TweetMessage;

public interface TweetTransformator {

    Message<TweetMessage> transformTweet(Message<TweetMessage> message);
}
