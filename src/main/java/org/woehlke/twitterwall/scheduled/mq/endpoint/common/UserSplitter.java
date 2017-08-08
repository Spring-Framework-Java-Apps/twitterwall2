package org.woehlke.twitterwall.scheduled.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

import java.util.List;

public interface UserSplitter {

    List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
