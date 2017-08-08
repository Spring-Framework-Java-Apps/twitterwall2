package org.woehlke.twitterwall.scheduled.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

import java.util.List;

public interface TweetsSplitter {

    List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message);
}
