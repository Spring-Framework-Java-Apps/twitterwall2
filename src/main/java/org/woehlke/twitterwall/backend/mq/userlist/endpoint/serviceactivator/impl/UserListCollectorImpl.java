package org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.userlist.endpoint.serviceactivator.UserListCollector;
import org.woehlke.twitterwall.backend.mq.userlist.msg.UserListMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessageBuilder;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserListService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

@Component("mqUserListCollector")
public class UserListCollectorImpl implements UserListCollector {


    @Override
    public Message<UserMessage> collectList(Message<List<UserListMessage>> incomingListOfUserMessage) {
        long idTwitterOfListOwningUser = (Long)incomingListOfUserMessage.getHeaders().get("idTwitterOfListOwningUser");
        long taskId = (Long)incomingListOfUserMessage.getHeaders().get("taskId");
        Task task = taskService.findById(taskId);
        User user = userService.findByIdTwitter(idTwitterOfListOwningUser);
        TaskMessage incomingTaskMessage = null;
        List<UserListMessage> in = incomingListOfUserMessage.getPayload();
        for(UserListMessage msgIn :in){
            UserListType userListType  = msgIn.getUserListType();
            UserList userList = msgIn.getUserList();
            incomingTaskMessage = msgIn.getTaskMessage();
            switch (userListType){
                case USERS_OWN_LIST:
                    user.getOwnLists().add(userList);
                    userList.setListOwner(user);
                    break;
                case USER_IS_MEMBER:
                    user.getUserListMemberships().add(userList);
                    userList.getMembers().add(user);
                    break;
                case USER_IS_SUBSCRIBER:
                    user.getUserListSubcriptions().add(userList);
                    userList.getSubscriber().add(user);
                    break;
            }
            userListService.store(userList,task);
        }
        userService.store(user,task);
        UserMessage outMsgPayload = new UserMessage(incomingTaskMessage, idTwitterOfListOwningUser);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(outMsgPayload)
                        .copyHeaders(incomingListOfUserMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }

    private final UserService userService;

    private final UserListService userListService;

    private final TaskService taskService;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public UserListCollectorImpl(UserService userService, UserListService userListService, TaskService taskService, UserMessageBuilder userMessageBuilder) {
        this.userService = userService;
        this.userListService = userListService;
        this.taskService = taskService;
        this.userMessageBuilder = userMessageBuilder;
    }
}
