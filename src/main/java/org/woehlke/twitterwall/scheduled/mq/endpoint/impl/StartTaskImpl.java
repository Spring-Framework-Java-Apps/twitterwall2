package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.StartTask;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.*;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

@Component("mqStartTask")
public class StartTaskImpl implements StartTask {

    @Override
    public Task fetchTweetsFromSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task updateUsers() {
        TaskType taskType = TaskType.UPDATE_USERS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task updateUsersFromMentions() {
        TaskType taskType = TaskType.UPDATE_USERS_FROM_MENTIONS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task fetchUsersFromList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_LIST;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task fetchFollower() {
        TaskType taskType = TaskType.FETCH_FOLLOWER;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task fetchFriends() {
        TaskType taskType = TaskType.FETCH_FRIENDS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task createTestDataForTweets() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_TWEETS;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task createTestDataForUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_USERS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task removeOldDataFromStorage() {
        TaskType taskType = TaskType.REMOVE_OLD_DATA_FROM_STORAGE;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public User createImprintUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_IMPRINT_USER;
        SendType sendType = SendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+" via MQ by "+sendType;
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType, sendType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, sendType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", sendType)
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+" via MQ by "+sendType;
            taskService.done(logMsg, task, countedEntities);
            log.info(logMsg);
            return msg.getUser();
        } else {
            logMsg = "Finished with Error: task "+taskType+" via MQ by "+sendType+": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
            return null;
        }
    }

    private Task sendAndReceiveTweet(TaskType taskType){
        SendType sendType = SendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+sendType;
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType,sendType,countedEntities);
        Message<TaskMessage> mqMessage = twitterwallMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TweetResultList){
            TweetResultList msg = (TweetResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+sendType;
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+sendType+": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private Task sendAndReceiveUser(TaskType taskType){
        SendType sendType = SendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+sendType;;
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(logMsg, taskType, sendType, countedEntities);
        Message<TaskMessage> mqMessage = twitterwallMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+sendType;
            taskService.done(logMsg,task,countedEntities);
            log.info(logMsg);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+sendType+": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private final MessageChannel startTaskChannel;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(StartTaskImpl.class);

    @Autowired
    public StartTaskImpl(MessageChannel startTaskChannel, TaskService taskService, CountedEntitiesService countedEntitiesService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.startTaskChannel = startTaskChannel;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

}


