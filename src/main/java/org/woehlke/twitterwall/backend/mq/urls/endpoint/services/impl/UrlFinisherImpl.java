package org.woehlke.twitterwall.backend.mq.urls.endpoint.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.urls.endpoint.services.UrlFinisher;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessage;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlMessageBuilder;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlResultList;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Component("mqUrlFinisher")
public class UrlFinisherImpl implements UrlFinisher {


    @Override
    public Message<UrlResultList> finish(Message<List<UrlMessage>> incomingMessageList) {
        List<Long> urlIdList = new ArrayList<>();
        long taskId = 0L;
        for(UrlMessage msg:incomingMessageList.getPayload()){
            urlIdList.add(msg.getUrlId());
            taskId = msg.getTaskMessage().getTaskId();
        }
        UrlResultList result = new UrlResultList(taskId,urlIdList);
        Message<UrlResultList> mqMessageOut = MessageBuilder.withPayload(result)
                .copyHeaders(incomingMessageList.getHeaders())
                .build();
        return mqMessageOut;
    }

    @Override
    public void finishAsnyc(Message<List<UrlMessage>> incomingMessageList) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        long taskId = 0L;
        for(UrlMessage msg:incomingMessageList.getPayload()){
            taskId = msg.getTaskMessage().getTaskId();
            break;
        }
        Task task = taskService.findById(taskId);
        String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by FIRE_AND_FORGET_SENDER";
        taskService.done(msgDone,task,countedEntities);
        log.info(msgDone);
    }

    private final UrlMessageBuilder urlMessageBuilder;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private static final Logger log = LoggerFactory.getLogger(UrlFinisherImpl.class);

    @Autowired
    public UrlFinisherImpl(UrlMessageBuilder urlMessageBuilder, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.urlMessageBuilder = urlMessageBuilder;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }
}
