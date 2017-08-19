package org.woehlke.twitterwall.scheduled.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

public interface TweetMessageBuilder {

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.entities.Tweet tweet, int loopId, int loopAll);

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll);

}
