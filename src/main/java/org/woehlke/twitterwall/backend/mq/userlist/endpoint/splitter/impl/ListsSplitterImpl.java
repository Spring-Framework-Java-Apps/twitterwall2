package org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.UserList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.parts.User2UserList;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.userlist.endpoint.splitter.ListsSplitter;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessageBuilder;
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

    @Override
    public List<Message<UserListMessage>> splitUserListMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserListMessage>> messageListOut = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        User2UserList fetchedUserList = twitterApiService.getLists();
        for (UserList userList: fetchedUserList.getOwnLists()) {
            UserListType type = UserListType.USERS_OWN_LIST;
            Message<UserListMessage> mqMessageOut = userListMessageBuilder.buildUserListMessageFromTask(
                    incomingTaskMessage,
                    userList,
                    fetchedUserList.getIdTwitterOfListOwningUser(),
                    type
            );
            messageListOut.add(mqMessageOut);
        }
        for (UserList userList: fetchedUserList.getUserListSubcriptions()) {
            UserListType type = UserListType.USER_IS_SUBSCRIBER;
            Message<UserListMessage> mqMessageOut = userListMessageBuilder.buildUserListMessageFromTask(
                    incomingTaskMessage,
                    userList,
                    fetchedUserList.getIdTwitterOfListOwningUser(),
                    type
            );
            messageListOut.add(mqMessageOut);
        }
        for (UserList userList: fetchedUserList.getUserListMemberships()) {
            UserListType type = UserListType.USER_IS_MEMBER;
            Message<UserListMessage> mqMessageOut = userListMessageBuilder.buildUserListMessageFromTask(
                    incomingTaskMessage,
                    userList,
                    fetchedUserList.getIdTwitterOfListOwningUser(),
                    type
            );
            messageListOut.add(mqMessageOut);
        }
        return messageListOut;
    }
}
