package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

import java.util.List;

public interface UpdateTweets {

    List<TweetMessage> splitMessage(Message<TaskMessage> message);
}
