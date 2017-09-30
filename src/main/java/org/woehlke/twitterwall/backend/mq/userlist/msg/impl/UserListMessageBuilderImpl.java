package org.woehlke.twitterwall.backend.mq.userlist.msg.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;

@Component
public class UserListMessageBuilderImpl implements UserListMessageBuilder {

    @Override
    public Message<UserListMessage> buildUserListMessageFromUser(Message<UserMessage> incomingUserMessage, UserList userList, long idTwitterOfListOwningUser,UserListType type) {
        UserListMessage outputPayload = new UserListMessage(incomingUserMessage.getPayload().getTaskMessage(), userList, idTwitterOfListOwningUser, type);
        Message<UserListMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingUserMessage.getHeaders())
                        .setHeader("idTwitterOfListOwningUser",idTwitterOfListOwningUser)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserListMessage> buildUserListMessageFromTask(Message<TaskMessage> incomingUserMessage, UserList userList, long idTwitterOfListOwningUser, UserListType type) {
        UserListMessage outputPayload = new UserListMessage(incomingUserMessage.getPayload(), userList, idTwitterOfListOwningUser, type);
        Message<UserListMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingUserMessage.getHeaders())
                        .setHeader("idTwitterOfListOwningUser",idTwitterOfListOwningUser)
                        .build();
        return mqMessageOut;
    }

    @Override
    public Message<UserListMessage> buildUserListMessageForTransformedUser(Message<UserListMessage> incomingMessage, org.woehlke.twitterwall.oodm.model.UserList userListOut,UserListType type) {
        long idTwitterOfThisUserList = incomingMessage.getPayload().getIdTwitterOfThisUserList();
        long idTwitterOfListOwningUser = incomingMessage.getPayload().getIdTwitterOfListOwningUser();
        UserListMessage retVal = new UserListMessage(incomingMessage.getPayload().getTaskMessage(),incomingMessage.getPayload().getUserListTwitter(),userListOut,idTwitterOfThisUserList, idTwitterOfListOwningUser, type);
        Message<UserListMessage> mqMessageOut = MessageBuilder.withPayload(retVal)
                .copyHeaders(incomingMessage.getHeaders())
                .setHeader("transformed",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }

}
