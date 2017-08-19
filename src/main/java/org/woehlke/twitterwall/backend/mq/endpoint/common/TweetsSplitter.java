package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.TweetMessage;

import java.util.List;

public interface TweetsSplitter {

    List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message);
}
