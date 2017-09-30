package org.woehlke.twitterwall.backend.mq.tasks.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.tasks.TaskResultList;
import org.woehlke.twitterwall.backend.mq.urls.msg.UrlResultList;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.tasks.TaskStart;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessageBuilder;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetResultList;

@Component("mqTaskStart")
public class TaskStartImpl implements TaskStart {

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
        TaskType taskType = TaskType.UPDATE_MENTIONS_FOR_USERS;
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
        TaskType taskType = TaskType.CREATE_TESTDATA_TWEETS;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task createTestDataForUser() {
        TaskType taskType = TaskType.CREATE_TESTDATA_USERS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task removeOldDataFromStorage() {
        TaskType taskType = TaskType.REMOVE_OLD_DATA_FROM_STORAGE;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getHomeTimeline() {
        TaskType taskType = TaskType.FETCH_HOME_TIMELINE;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getUserTimeline() {
        TaskType taskType = TaskType.FETCH_USER_TIMELINE;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getMentions() {
        TaskType taskType = TaskType.FETCH_MENTIONS;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getFavorites() {
        TaskType taskType = TaskType.FETCH_FAVORITES;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getRetweetsOfMe() {
        TaskType taskType = TaskType.FETCH_RETWEETS_OF_ME;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task getLists() {
        TaskType taskType = TaskType.FETCH_LISTS;
        return sendAndReceiveUserList(taskType);
    }

    @Override
    public Task createImprintUserAsync() {
        TaskType taskType = TaskType.CREATE_IMPRINT_USER;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task fetchUserlistOwners() {
        TaskType taskType = TaskType.FETCH_USERLIST_OWNERS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task startGarbageCollection() {
        TaskType taskType = TaskType.GARBAGE_COLLECTION;
        return sendAndReceiveTask(taskType);
    }

    @Override
    public Task startUpdateUrls() {
        TaskType taskType = TaskType.UPDATE_URLS;
        return sendAndReceiveUrl(taskType);
    }

    @Override
    public Task startFetchListOwner() {
        return null;
    }

    @Override
    public Task startFetchListsForUsers() {
        return null;
    }

    private Task sendAndReceiveTask(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+ taskSendType;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType, taskSendType,countedEntities);
        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TaskResultList){
            TaskResultList msg = (TaskResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+ taskSendType;
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private Task sendAndReceiveUrl(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+ taskSendType;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType, taskSendType,countedEntities);
        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UrlResultList){
            UrlResultList msg = (UrlResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+ taskSendType;
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private Task sendAndReceiveUserList(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+ taskSendType;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType, taskSendType,countedEntities);
        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TweetResultList){
            TweetResultList msg = (TweetResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+ taskSendType;
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    @Override
    public User createImprintUser() {
        TaskType taskType = TaskType.CREATE_IMPRINT_USER;
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+" via MQ by "+ taskSendType;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType, taskSendType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, taskSendType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", taskSendType)
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+" via MQ by "+ taskSendType;
            taskService.done(logMsg, task, countedEntities);
            log.debug(logMsg);
            return msg.getUser();
        } else {
            logMsg = "Finished with Error: task "+taskType+" via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
            return null;
        }
    }

    private Task sendAndReceiveTweet(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+ taskSendType;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType, taskSendType,countedEntities);
        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TweetResultList){
            TweetResultList msg = (TweetResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+ taskSendType;
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private Task sendAndReceiveUser(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.SEND_AND_WAIT_FOR_RESULT;
        String logMsg = "Start task "+taskType+"via MQ by "+ taskSendType;;
        log.debug(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(logMsg, taskType, taskSendType, countedEntities);
        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(channelTaskStart, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by "+ taskSendType;
            taskService.done(logMsg,task,countedEntities);
            log.debug(logMsg);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by "+ taskSendType +": Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }


    private final MessageChannel channelTaskStart;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TaskMessageBuilder taskMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(TaskStartImpl.class);

    @Autowired
    public TaskStartImpl(@Qualifier("channel.TaskStart") MessageChannel channelTaskStart, TaskService taskService, CountedEntitiesService countedEntitiesService, TaskMessageBuilder taskMessageBuilder) {
        this.channelTaskStart = channelTaskStart;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.taskMessageBuilder = taskMessageBuilder;
    }

}


