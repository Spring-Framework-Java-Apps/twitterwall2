package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.channel.SenderType;
import org.woehlke.twitterwall.scheduled.mq.endoint.UserFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;
import org.woehlke.twitterwall.scheduled.service.backend.impl.TwitterApiServiceImpl;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqUserFinisher")
public class UserFinisherImpl implements UserFinisher {

    @Override
    public UserResultList finish(Message<List<UserMessage>> incomingMessageList) {
        long taskId = 0L;
        List<User> users = new ArrayList<>();
        List<UserMessage> userMessageList = incomingMessageList.getPayload();
        if(userMessageList.size()>0) {
            taskId = userMessageList.get(0).getTaskId();
        }
        for(UserMessage msg :userMessageList){
            users.add(msg.getUser());
        }
        UserResultList userResultList = new UserResultList(taskId,users);
        return userResultList;
    }

    @Override
    public void finishAsnyc(Message<List<UserMessage>> incomingMessageList) {
        List<UserMessage> userMessageList = incomingMessageList.getPayload();
        CountedEntities countedEntities = countedEntitiesService.countAll();
        if(incomingMessageList.getHeaders().containsKey("task_id")){
            long taskId = (Long) incomingMessageList.getHeaders().get( "task_id");
            Task task = taskService.findById(taskId);
            String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER;
            taskService.done(msgDone,task,countedEntities);
            log.info(msgDone);
        } else {
            if(userMessageList.size()>0) {
                long taskId = userMessageList.get(0).getTaskId();
                Task task = taskService.findById(taskId);
                String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER;
                taskService.done(task,countedEntities);
                log.info(msgDone);
            } else {
                log.warn("finishAsnyc: No Users performed. via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER);
            }
        }
    }


    @Autowired
    public UserFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
