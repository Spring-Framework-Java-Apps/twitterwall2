package org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.mentions.endpoint.serviceactivator.UserforMentionTransformator;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.mentions.msg.MentionMessageBuilder;
import org.woehlke.twitterwall.backend.service.transform.UserTransformService;

@Component("mqUserforMentionTransformator")
public class UserforMentionTransformatorImpl implements UserforMentionTransformator {

    @Override
    public Message<MentionMessage> transformUserforMention(Message<MentionMessage> incomingMessage) {
        MentionMessage receivedMessage = incomingMessage.getPayload();
        if(receivedMessage.isIgnoreNextSteps()){
            Message<MentionMessage> mqMessageOut = mentionMessageBuilder.buildMentionMessage(incomingMessage);
            return mqMessageOut;
        } else {
            long id = receivedMessage.getTaskMessage().getTaskId();
            Task task = taskService.findById(id);
            User user = userTransformService.transform(receivedMessage.getTwitterProfile(),task);
            Message<MentionMessage> mqMessageOut = mentionMessageBuilder.buildMentionMessage(incomingMessage,user);
            return mqMessageOut;
        }
    }

    private final UserTransformService userTransformService;

    private final TaskService taskService;

    private final MentionMessageBuilder mentionMessageBuilder;

    @Autowired
    public UserforMentionTransformatorImpl(UserTransformService userTransformService, TaskService taskService, MentionMessageBuilder mentionMessageBuilder) {
        this.userTransformService = userTransformService;
        this.taskService = taskService;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }
}
