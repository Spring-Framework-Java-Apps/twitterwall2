package org.woehlke.twitterwall.backend.mq.userlist.msg;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;

public interface UserListMessageBuilder {

    Message<UserListMessage> buildUserListMessageFromUser(Message<UserMessage> incomingUserMessage, UserList userList, long userIdTwitter,UserListType type);

    Message<UserListMessage> buildUserListMessageFromTask(Message<TaskMessage> incomingUserMessage, UserList userList, long userIdTwitter, UserListType type);

    Message<UserListMessage> buildUserListMessageForTransformedUser(Message<UserListMessage> incomingMessage, org.woehlke.twitterwall.oodm.model.UserList userListOut,UserListType type);
}
