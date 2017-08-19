package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;

import java.util.List;

public interface UserSplitter {

    List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
