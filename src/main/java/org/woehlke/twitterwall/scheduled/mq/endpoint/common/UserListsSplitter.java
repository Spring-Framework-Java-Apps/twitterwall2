package org.woehlke.twitterwall.scheduled.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

import java.util.List;

public interface UserListsSplitter {

    List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage);
}
