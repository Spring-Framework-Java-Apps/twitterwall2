package org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;

public interface UserListMessageBuilder {

    Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll);

}
