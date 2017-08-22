package org.woehlke.twitterwall.backend.mq.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;

import java.util.List;

public interface TweetsSplitter {

    List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message);
}
