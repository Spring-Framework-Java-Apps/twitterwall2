package org.woehlke.twitterwall.backend.mq.endpoint.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessage;

import java.util.List;

public interface UserListsSplitter {

    List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage);
}
