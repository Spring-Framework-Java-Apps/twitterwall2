package org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.mentions.services.MentionFinisher;
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.impl.UserFinisherImpl;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.MentionResultList;

import java.util.ArrayList;
import java.util.List;

@Component("mqMentionFinisher")
public class MentionFinisherImpl implements MentionFinisher {

    public MentionFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
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

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
