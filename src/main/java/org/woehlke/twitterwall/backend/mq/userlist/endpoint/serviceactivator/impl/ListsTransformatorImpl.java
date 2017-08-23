package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator.ListsTransformator;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.service.transform.UserListTransformService;


@Component("mqUserListTransformator")
public class ListsTransformatorImpl implements ListsTransformator {


    @Override
    public Message<UserListMessage> transformList(Message<UserListMessage> incomingMessage) {
        long taskId = incomingMessage.getPayload().getTaskMessage().getTaskId();
        Task task = taskService.findById(taskId);
        UserListType userListType = incomingMessage.getPayload().getUserListType();
        org.woehlke.twitterwall.oodm.model.UserList userListOut = userListTransformService.transform(incomingMessage.getPayload().getUserListTwitter(),task);
        Message<UserListMessage> mqMessageOut = userListMessageBuilder.buildUserListMessageForTransformedUser(incomingMessage,userListOut,userListType);
        return mqMessageOut;
    }


    private final TaskService taskService;

    private final UserListTransformService userListTransformService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public ListsTransformatorImpl(TaskService taskService, UserListTransformService userListTransformService, UserListMessageBuilder userListMessageBuilder) {
        this.taskService = taskService;
        this.userListTransformService = userListTransformService;
        this.userListMessageBuilder = userListMessageBuilder;
    }
}
