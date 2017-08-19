package org.woehlke.twitterwall.backend.mq.endpoint.userlist.endpoint.serviceactivator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.endpoint.serviceactivator.ListFinisher;
import org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.serviceactivator.impl.UserFinisherImpl;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListResultList;

import java.util.ArrayList;
import java.util.List;

@Component("mqUserListsFinisher")
public class ListFinisherImpl implements ListFinisher {

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public Message<UserListResultList> finish(Message<List<UserListMessage>> incomingMessageList) {
        List<UserList> resultList = new ArrayList<>();
        long taskId = 0L;
        for(UserListMessage msg:incomingMessageList.getPayload()){
            resultList.add( msg.getUserList());
            taskId = msg.getTaskMessage().getTaskId();
        }
        UserListResultList result = new UserListResultList(taskId,resultList);
        Message<UserListResultList> mqMessageOut = MessageBuilder.withPayload(result)
                .copyHeaders(incomingMessageList.getHeaders())
                .setHeader("persisted",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public void finishAsnyc(Message<List<UserListMessage>> incomingMessageList) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        long taskId = 0L;
        for(UserListMessage msg:incomingMessageList.getPayload()){
            taskId = msg.getTaskMessage().getTaskId();
            break;
        }
        Task task = taskService.findById(taskId);
        String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by FIRE_AND_FORGET_SENDER";
        taskService.done(msgDone,task,countedEntities);
        log.info(msgDone);
    }

    @Autowired
    public ListFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, UserListMessageBuilder userListMessageBuilder) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.userListMessageBuilder = userListMessageBuilder;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserListMessageBuilder userListMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
