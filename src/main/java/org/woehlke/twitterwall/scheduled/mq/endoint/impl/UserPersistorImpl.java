package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.mq.endoint.UserPersistor;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;

@Component("mqUserPersistor")
public class UserPersistorImpl implements UserPersistor {

    private final TaskService taskService;

    private final StoreUserProcess storeUserProcess;

    @Autowired
    public UserPersistorImpl(TaskService taskService, StoreUserProcess storeUserProcess) {
        this.taskService = taskService;
        this.storeUserProcess = storeUserProcess;
    }

    @Override
    public UserMessage persistUser(Message<UserMessage> incomingUserMessage) {
        UserMessage receivedMessage = incomingUserMessage.getPayload();
        long id = receivedMessage.getTwitterProfileMessage().getTaskMessage().getTaskId();
        Task task = taskService.findById(id);
        User user = storeUserProcess.storeUserProcess(receivedMessage.getUser(),task);
        UserMessage userMessage = new UserMessage(user,id);
        return userMessage;
    }
}
