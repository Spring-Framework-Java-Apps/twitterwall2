package org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessageBuilder;
import org.woehlke.twitterwall.backend.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator.UserPersistor;

@Component("mqUserPersistor")
public class UserPersistorImpl implements UserPersistor {

    private final TaskService taskService;

    private final StoreUserProcess storeUserProcess;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public UserPersistorImpl(TaskService taskService, StoreUserProcess storeUserProcess, UserMessageBuilder userMessageBuilder) {
        this.taskService = taskService;
        this.storeUserProcess = storeUserProcess;
        this.userMessageBuilder = userMessageBuilder;
    }

    @Override
    public Message<UserMessage> persistUser(Message<UserMessage> incomingUserMessage) {
        UserMessage receivedMessage = incomingUserMessage.getPayload();
        if(receivedMessage.isIgnoreTransformation()) {
            return MessageBuilder.withPayload(receivedMessage)
                        .copyHeaders(incomingUserMessage.getHeaders())
                        .setHeader("persisted",Boolean.TRUE)
                        .build();
        } else {
            long taskId = receivedMessage.getTaskMessage().getTaskId();
            Task task = taskService.findById(taskId);

            User user = receivedMessage.getUser();

            User userPers = storeUserProcess.storeUserProcess(user, task);
            UserMessage mqMessageOut = new UserMessage(
                    receivedMessage.getTaskMessage(),
                    receivedMessage.getTwitterProfile(),
                    userPers
            );
            return MessageBuilder.withPayload(mqMessageOut)
                    .copyHeaders(incomingUserMessage.getHeaders())
                    .setHeader("persisted",Boolean.TRUE)
                    .build();
        }
    }
}
