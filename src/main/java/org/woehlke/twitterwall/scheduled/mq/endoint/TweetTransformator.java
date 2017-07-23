package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;

public interface TweetTransformator {

    Message<TweetFromTwitter> transformTweet(Message<TweetFromTwitter> message);
}
