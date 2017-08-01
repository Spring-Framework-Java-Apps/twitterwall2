package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;

import java.util.List;

public interface CreateTestDataForTweets {

    List<TweetFromTwitter> splitMessage(Message<TaskMessage> message);

}
