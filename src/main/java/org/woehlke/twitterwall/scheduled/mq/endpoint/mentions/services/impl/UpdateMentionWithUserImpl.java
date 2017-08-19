package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.UpdateMentionWithUser;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.MentionMessageBuilder;

@Component("mqUpdateMentionWithUser")
public class UpdateMentionWithUserImpl implements UpdateMentionWithUser {

    @Override
    public Message<MentionMessage> updateMentionWithUser(Message<MentionMessage> incomingMessage) {
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
            long idTwitterOfUser = user.getIdTwitter();
            long idOfUser = user.getId();
            long mentionId = receivedMessage.getMentionId();
            Mention mention = mentionService.findById(mentionId);
            mention.setIdOfUser(idOfUser);
            mention.setIdTwitterOfUser(idTwitterOfUser);
            mention = mentionService.store(mention,task);
            Message<MentionMessage> mqMessageOut = mentionMessageBuilder.buildMentionMessage(incomingMessage,mention);
            return mqMessageOut;
        }
    }

    private final TaskService taskService;

    private final MentionService mentionService;

    private final MentionMessageBuilder mentionMessageBuilder;

    @Autowired
    public UpdateMentionWithUserImpl(TaskService taskService, MentionService mentionService, MentionMessageBuilder mentionMessageBuilder) {
        this.taskService = taskService;
        this.mentionService = mentionService;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }
}
