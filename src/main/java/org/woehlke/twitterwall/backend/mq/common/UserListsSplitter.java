package org.woehlke.twitterwall.backend.mq.common;

import org.springframework.messaging.Message;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;

import java.util.List;

public interface UserListsSplitter {

    List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage);
}
