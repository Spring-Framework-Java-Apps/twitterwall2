package org.woehlke.twitterwall.backend.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.TweetMessage;

public interface TweetMessageBuilder {

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.model.Tweet tweet, int loopId, int loopAll);

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll);

}
