package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.ListsTransformator;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.UserListMessageBuilder;
import org.woehlke.twitterwall.scheduled.service.transform.UserListTransformService;

@Component("mqUserListTransformator")
public class ListsTransformatorImpl implements ListsTransformator {

    private final TaskService taskService;

    private final UserListTransformService userListTransformService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public ListsTransformatorImpl(TaskService taskService, UserListTransformService userListTransformService, UserListMessageBuilder userListMessageBuilder) {
        this.taskService = taskService;
        this.userListTransformService = userListTransformService;
        this.userListMessageBuilder = userListMessageBuilder;
    }

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage) {
        long taskId = incomingMessage.getPayload().getTaskMessage().getTaskId();
        Task task = taskService.findById(taskId);
        org.woehlke.twitterwall.oodm.model.UserList userListOut = userListTransformService.transform(incomingMessage.getPayload().getUserListTwitter(),task);
        UserListMessage retVal = new UserListMessage(incomingMessage.getPayload().getTaskMessage(),incomingMessage.getPayload().getUserListTwitter(),userListOut);
        Message<UserListMessage> mqMessageOut = MessageBuilder.withPayload(retVal)
                .copyHeaders(incomingMessage.getHeaders())
                .setHeader("transformed",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }
}
