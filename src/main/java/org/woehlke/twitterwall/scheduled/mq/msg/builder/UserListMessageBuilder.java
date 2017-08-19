package org.woehlke.twitterwall.scheduled.mq.msg.builder;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;

public interface UserListMessageBuilder {

    Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll);

}
