package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.mq.endpoint.UserPersistor;

@Component("mqUserPersistor")
public class UserPersistorImpl implements UserPersistor {

    private final TaskService taskService;

    private final StoreUserProcess storeUserProcess;

    @Autowired
    public UserPersistorImpl(TaskService taskService, StoreUserProcess storeUserProcess) {
        this.taskService = taskService;
        this.storeUserProcess = storeUserProcess;
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
                    user
            );
            return MessageBuilder.withPayload(mqMessageOut)
                    .copyHeaders(incomingUserMessage.getHeaders())
                    .setHeader("persisted",Boolean.TRUE)
                    .build();
        }
    }
}
