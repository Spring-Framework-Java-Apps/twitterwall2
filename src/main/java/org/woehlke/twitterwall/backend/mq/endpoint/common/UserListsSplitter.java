package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.UserListMessage;

import java.util.List;

public interface UserListsSplitter {

    List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage);
}
