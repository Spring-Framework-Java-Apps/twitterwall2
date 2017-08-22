package org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.FetchUserListsForUsers;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

@Component("mqFetchUserListsForUsers")
public class FetchUserListsForUsersImpl implements FetchUserListsForUsers {


    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<UserMessage> incomingTaskMessage) {
        return null;
    }

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public FetchUserListsForUsersImpl(TwitterApiService twitterApiService, TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService, UserListMessageBuilder userListMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.userListMessageBuilder = userListMessageBuilder;
    }
}
