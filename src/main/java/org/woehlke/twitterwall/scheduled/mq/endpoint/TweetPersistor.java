package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;

public interface TweetPersistor {

    Message<TweetFromTwitter> persistTweet(Message<TweetFromTwitter> incomingUserMessage);
}
