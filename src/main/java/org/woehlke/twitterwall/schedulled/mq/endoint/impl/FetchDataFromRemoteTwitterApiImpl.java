package org.woehlke.twitterwall.schedulled.mq.endoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.schedulled.mq.endoint.FetchDataFromRemoteTwitterApi;
import org.woehlke.twitterwall.schedulled.mq.msg.TaskMessage;

@Component("mqFetchDataFromRemoteTwitterApi")
public class FetchDataFromRemoteTwitterApiImpl implements FetchDataFromRemoteTwitterApi {

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromRemoteTwitterApiImpl.class);

    private final TaskService taskService;

    private final TwitterApiService twitterApiService;

    @Autowired
    public FetchDataFromRemoteTwitterApiImpl(TaskService taskService, TwitterApiService twitterApiService) {
        this.taskService = taskService;
        this.twitterApiService = twitterApiService;
    }

    private void react(String logMsg,Message<TaskMessage> mqMessage){
        TaskMessage receivedMessage = mqMessage.getPayload();
        Task task = taskService.findById(receivedMessage.getTaskId());
        taskService.start(task);
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"received: "+mqMessage.getPayload().toString());
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
    }

    @Override
    public void fetchTweetsFromTwitterSearch(Message<TaskMessage> mqMessage) {
        String logMsg = "fetchTweetsFromTwitterSearch: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void fetchUsersFromDefinedUserList(Message<TaskMessage> mqMessage) {
        String logMsg = "fetchUsersFromDefinedUserList: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void createTestDataForUser(Message<TaskMessage> mqMessage) {
        String logMsg = "createTestDataForUser: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void createtestDataForTweets(Message<TaskMessage> mqMessage) {
        String logMsg = "createtestDataForTweets: ";
        this.react(logMsg, mqMessage);
    }

    @Override
    public void createImprintUser(Message<TaskMessage> mqMessage) {
        String logMsg = "createImprintUser: ";
        this.react(logMsg, mqMessage);
    }

}
