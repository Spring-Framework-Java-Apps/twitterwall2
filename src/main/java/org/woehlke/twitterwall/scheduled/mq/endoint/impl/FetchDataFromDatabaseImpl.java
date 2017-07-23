package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.FetchDataFromDatabase;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

@Component("mqFetchDataFromDatabase")
public class FetchDataFromDatabaseImpl implements FetchDataFromDatabase {

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromDatabaseImpl.class);

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public FetchDataFromDatabaseImpl(TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }


    private void react(String logMsg,Message<TaskMessage> mqMessage){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskMessage receivedMessage = mqMessage.getPayload();
        Task task = taskService.findById(receivedMessage.getTaskId());
        taskService.start(task,countedEntities);
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"received: "+mqMessage.getPayload().toString());
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
        log.info(logMsg+"##############################################");
    }

    @Override
    public void updateTweets(Message<TaskMessage> mqMessage) {
        String logMsg = "updateTweets: ";
        react(logMsg,mqMessage);
    }

    @Override
    public void updateUserProfiles(Message<TaskMessage> mqMessage) {
        String logMsg = "updateUserProfiles: ";
        react(logMsg,mqMessage);
    }

    @Override
    public void updateUserProfilesFromMentions(Message<TaskMessage> mqMessage) {
        String logMsg = "updateUserProfilesFromMentions: ";
        react(logMsg,mqMessage);
    }
}
