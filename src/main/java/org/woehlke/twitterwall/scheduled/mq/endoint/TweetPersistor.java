package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMsg;

public interface TweetPersistor {

    Message<TweetFromTwitter> persistTweet(Message<TweetFromTwitter> incomingUserMessage);
}
