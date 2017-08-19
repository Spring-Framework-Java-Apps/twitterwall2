package org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;

public interface TweetMessageBuilder {

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, org.woehlke.twitterwall.oodm.model.Tweet tweet, int loopId, int loopAll);

    Message<TweetMessage> buildTweetMessage(Message<TaskMessage> incomingTaskMessage, Tweet tweet, int loopId, int loopAll);

}
