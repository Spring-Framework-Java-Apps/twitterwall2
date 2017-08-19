package org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserListService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.userlist.services.ListsPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserListMessageBuilder;

@Component("mqUserListPersistor")
public class ListsPersistorImpl implements ListsPersistor {

    private final TaskService taskService;

    private final UserListService userListService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public ListsPersistorImpl(TaskService taskService, UserListService userListService, UserListMessageBuilder userListMessageBuilder) {
        this.taskService = taskService;
        this.userListService = userListService;
        this.userListMessageBuilder = userListMessageBuilder;
    }

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListMessage> persistList(Message<UserListMessage> incomingMessage) {
        UserListMessage receivedMessage = incomingMessage.getPayload();
        long taskId = receivedMessage.getTaskMessage().getTaskId();
        Task task = taskService.findById(taskId);
        UserList userListOut = userListService.store(receivedMessage.getUserList(),task);
        UserListMessage newUserListMsg = new UserListMessage(receivedMessage.getTaskMessage(),receivedMessage.getUserListTwitter(),userListOut);
        Message<UserListMessage> mqMessageOut = MessageBuilder.withPayload(newUserListMsg)
                .copyHeaders(incomingMessage.getHeaders())
                .setHeader("persisted",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }
}
