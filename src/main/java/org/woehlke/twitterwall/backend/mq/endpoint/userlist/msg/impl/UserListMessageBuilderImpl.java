package org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessageBuilder;

@Component
public class UserListMessageBuilderImpl implements UserListMessageBuilder {

    @Override
    public Message<UserListMessage> buildUserListMessage(Message<TaskMessage> incomingTaskMessage, UserList userList, int loopId, int loopAll) {
        UserListMessage outputPayload = new UserListMessage(incomingTaskMessage.getPayload(),userList);
        Message<UserListMessage> mqMessageOut =
                MessageBuilder.withPayload(outputPayload)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("loop_id",loopId)
                        .setHeader("loop_all",loopAll)
                        .build();
        return mqMessageOut;
    }

}
