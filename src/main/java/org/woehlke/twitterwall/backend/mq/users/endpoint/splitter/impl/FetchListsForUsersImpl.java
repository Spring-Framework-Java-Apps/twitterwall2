package org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.FetchListsForUsers;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

@Component("mqFetchListsForUsers")
public class FetchListsForUsersImpl implements FetchListsForUsers {


    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        return null;
    }

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public FetchListsForUsersImpl(TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService, UserListMessageBuilder userListMessageBuilder) {
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.userListMessageBuilder = userListMessageBuilder;
    }

}
