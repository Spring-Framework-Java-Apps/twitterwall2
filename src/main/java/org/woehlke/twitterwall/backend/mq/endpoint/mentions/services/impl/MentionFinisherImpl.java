package org.woehlke.twitterwall.backend.mq.endpoint.mentions.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.mentions.services.MentionFinisher;
import org.woehlke.twitterwall.backend.mq.endpoint.users.services.impl.UserFinisherImpl;
import org.woehlke.twitterwall.backend.mq.msg.MentionMessage;
import org.woehlke.twitterwall.backend.mq.msg.builder.MentionMessageBuilder;
import org.woehlke.twitterwall.backend.mq.msg.results.MentionResultList;

import java.util.ArrayList;
import java.util.List;

@Component("mqMentionFinisher")
public class MentionFinisherImpl implements MentionFinisher {

    @Autowired
    public MentionFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, MentionMessageBuilder mentionMessageBuilder) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.mentionMessageBuilder = mentionMessageBuilder;
    }

    @Override
    public Message<MentionResultList> finish(Message<List<MentionMessage>> incomingMessageList) {
        long taskId = 0L;
        List<Long> mentionIds = new ArrayList<>();
        List<MentionMessage> mentionMessageList = incomingMessageList.getPayload();
        for(MentionMessage msg :mentionMessageList){
            taskId = msg.getTaskMessage().getTaskId();
            mentionIds.add(msg.getMentionId());
        }
        MentionResultList userResultList = new MentionResultList(taskId,mentionIds);
        Message<MentionResultList> mqMessageOut = MessageBuilder.withPayload(userResultList)
                .copyHeaders(incomingMessageList.getHeaders())
                .setHeader("fnished",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }

    @Override
    public void finishAsnyc(Message<List<MentionMessage>> incomingMessageList) {
        List<MentionMessage> userMessageList = incomingMessageList.getPayload();
        CountedEntities countedEntities = countedEntitiesService.countAll();
        long taskId=0L;
        for(MentionMessage msg :userMessageList){
            taskId = msg.getTaskMessage().getTaskId();
            break;
        }
        Task task = taskService.findById(taskId);
        String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by FIRE_AND_FORGET_SENDER";
        taskService.done(msgDone,task,countedEntities);
        log.info(msgDone);
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final MentionMessageBuilder mentionMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
