package org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator.UserforMentionPersistor;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessageBuilder;
import org.woehlke.twitterwall.backend.service.persist.StoreUserProcess;

@Component("mqUserforMentionPersistor")
public class UserforMentionPersistorImpl implements UserforMentionPersistor {

    @Override
    public Message<MentionMessage> persistUserforMention(Message<MentionMessage> incomingMessage) {
        MentionMessage receivedMessage = incomingMessage.getPayload();
        if(receivedMessage.getTwitterProfile()==null) {
            return MessageBuilder.withPayload(receivedMessage)
                    .copyHeaders(incomingMessage.getHeaders())
                    .setHeader("persisted",Boolean.TRUE)
                    .build();
        } else {
            long taskId = receivedMessage.getTaskMessage().getTaskId();
            Task task = taskService.findById(taskId);
            User user = receivedMessage.getUser();
            User userPers = storeUserProcess.storeUserProcess(user, task);
            Message<MentionMessage> mqMessageOut = mentionMessageBuilder.buildMentionMessage(incomingMessage,userPers);
            return mqMessageOut;
        }
    }

    private final TaskService taskService;

    private final StoreUserProcess storeUserProcess;

    private final MentionMessageBuilder mentionMessageBuilder;

    @Autowired
    public UserforMentionPersistorImpl(TaskService taskService, StoreUserProcess storeUserProcess, MentionMessageBuilder mentionMessageBuilder) {
        this.taskService = taskService;
        this.storeUserProcess = storeUserProcess;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }
}
