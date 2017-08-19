package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;

public interface UserListMessageBuilder {

    Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll);

}
