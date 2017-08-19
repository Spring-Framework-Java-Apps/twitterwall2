package org.woehlke.twitterwall.backend.mq.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;

import java.util.List;

public interface UserSplitter {

    List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage);
}
