package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.tweets.msg.TweetMessage;

import java.util.List;

public interface TweetsSplitter {

    List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message);
}
