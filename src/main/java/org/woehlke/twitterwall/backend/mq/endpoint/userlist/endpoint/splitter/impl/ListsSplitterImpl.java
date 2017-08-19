package org.woehlke.twitterwall.backend.mq.endpoint.userlist.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.endpoint.splitter.ListsSplitter;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg.UserListMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqUserListsSplitter")
public class ListsSplitterImpl implements ListsSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserListMessageBuilder userListMessageBuilder;

    @Autowired
    public ListsSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, UserListMessageBuilder userListMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.userListMessageBuilder = userListMessageBuilder;
    }

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserListMessage>> messageListOut = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<UserList> fetchedUserList = twitterApiService.getLists();
        int loopId = 0;
        int loopAll = fetchedUserList.size();
        for (UserList userList: fetchedUserList) {
            loopId++;
            Message<UserListMessage> mqMessageOut = userListMessageBuilder.buildUserListMessage(incomingTaskMessage,userList,loopId,loopAll);
            messageListOut.add(mqMessageOut);
        }
        return messageListOut;
    }
}
