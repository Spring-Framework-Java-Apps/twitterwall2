package org.woehlke.twitterwall.backend.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.UserListMessage;

public interface UserListMessageBuilder {

    Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll);

}
