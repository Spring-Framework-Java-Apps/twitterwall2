package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;

public interface TweetMessageBuilder {

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.entities.Tweet tweet, int loopId, int loopAll);

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll);

}
