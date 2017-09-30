package org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.FetchListsForUsers;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessageBuilder;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchListsForUsers")
public class FetchListsForUsersImpl implements FetchListsForUsers {


    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        TaskMessage in = incomingTaskMessage.getPayload();
        Task task = taskService.findById(in.getTaskId());
        CountedEntities counted = countedEntitiesService.countAll();
        task = taskService.start(task,counted);
        List<Message<UserMessage>> result = new ArrayList<>();
        List<Long> idTwitterOfAllUsers = userService.getIdTwitterOfAllUsers();
        int loopId = 0;
        int loopAll = idTwitterOfAllUsers.size();
        for(long userIdTwitter:idTwitterOfAllUsers){
            loopId++;
            Message<UserMessage> out = userMessageBuilder.buildUserMessageForUser(incomingTaskMessage,userIdTwitter,loopId,loopAll);
            result.add(out);
        }
        return result;
    }

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public FetchListsForUsersImpl(TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService, UserMessageBuilder userMessageBuilder) {
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.userMessageBuilder = userMessageBuilder;
    }

}
