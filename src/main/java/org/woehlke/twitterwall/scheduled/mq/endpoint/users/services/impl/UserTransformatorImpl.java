package org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.UserTransformator;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

@Component("mqUserTransformator")
public class UserTransformatorImpl implements UserTransformator {

    private final UserTransformService userTransformService;

    private final TaskService taskService;

    @Autowired
    public UserTransformatorImpl(UserTransformService userTransformService, TaskService taskService) {
        this.userTransformService = userTransformService;
        this.taskService = taskService;
    }

    @Override
    public Message<UserMessage> transformUser(Message<UserMessage> mqMessageIn) {
        UserMessage receivedMessage = mqMessageIn.getPayload();
        if(receivedMessage.isIgnoreTransformation()){
            Message<UserMessage> mqMessageOut = MessageBuilder.withPayload(receivedMessage)
                    .copyHeaders(mqMessageIn.getHeaders())
                    .setHeader("transformed",Boolean.TRUE)
                    .build();
            return mqMessageOut;
        } else {
            long id = receivedMessage.getTaskMessage().getTaskId();
            Task task = taskService.findById(id);
            User user = userTransformService.transform(receivedMessage.getTwitterProfile(),task);
            UserMessage outMsg = new UserMessage(
                    receivedMessage.getTaskMessage(),
                    receivedMessage.getTwitterProfile(),
                    user
            );
            Message<UserMessage> mqMessageOut =
                    MessageBuilder.withPayload(outMsg)
                    .copyHeaders(mqMessageIn.getHeaders())
                    .setHeader("transformed",Boolean.TRUE)
                    .build();
            return mqMessageOut;
        }
    }
}
