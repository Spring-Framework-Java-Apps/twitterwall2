package org.woehlke.twitterwall.backend.mq.tweets.endpoint.serviceactivator;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;

public interface TweetTransformator {

    Message<TweetMessage> transformTweet(Message<TweetMessage> message);
}
