package org.woehlke.twitterwall.scheduled.mq.endpoint.tasks.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tasks.AsyncStartTask;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.TaskMessageBuilder;

@Component("mqAsyncStartTask")
public class AsyncStartTaskImpl implements AsyncStartTask {

    @Override
    public Task fetchTweetsFromSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        return send(taskType);
    }

    @Override
    public Task updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        return send(taskType);
    }

    @Override
    public Task updateUsers() {
        TaskType taskType = TaskType.UPDATE_USERS;
        return send(taskType);
    }

    @Override
    public Task updateUsersFromMentions() {
        TaskType taskType = TaskType.UPDATE_MENTIONS_FOR_USERS;
        return send(taskType);
    }

    @Override
    public Task fetchUsersFromList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_LIST;
        return send(taskType);
    }

    @Override
    public Task fetchFollower() {
        TaskType taskType = TaskType.FETCH_FOLLOWER;
        return send(taskType);
    }

    @Override
    public Task fetchFriends() {
        TaskType taskType = TaskType.FETCH_FRIENDS;
        return send(taskType);
    }

    @Override
    public Task createTestDataForTweets() {
        TaskType taskType = TaskType.CREATE_TESTDATA_TWEETS;
        return send(taskType);
    }

    @Override
    public Task createTestDataForUser() {
        TaskType taskType = TaskType.CREATE_TESTDATA_USERS;
        return send(taskType);
    }

    @Override
    public Task removeOldDataFromStorage() {
        TaskType taskType = TaskType.REMOVE_OLD_DATA_FROM_STORAGE;
        return send(taskType);
    }

    @Override
    public Task getHomeTimeline() {
        TaskType taskType = TaskType.FETCH_HOME_TIMELINE;
        return send(taskType);
    }

    @Override
    public Task getUserTimeline() {
        TaskType taskType = TaskType.FETCH_USER_TIMELINE;
        return send(taskType);
    }

    @Override
    public Task getMentions() {
        TaskType taskType = TaskType.FETCH_MENTIONS;
        return send(taskType);
    }

    @Override
    public Task getFavorites() {
        TaskType taskType = TaskType.FETCH_FAVORITES;
        return send(taskType);
    }

    @Override
    public Task getRetweetsOfMe() {
        TaskType taskType = TaskType.FETCH_RETWEETS_OF_ME;
        return send(taskType);
    }

    @Override
    public Task getLists() {
        TaskType taskType = TaskType.FETCH_LISTS;
        return send(taskType);
    }

    @Override
    public Task createImprintUserAsync() {
        TaskType taskType = TaskType.CREATE_IMPRINT_USER;
        return send(taskType);
    }

    @Override
    public Task fetchUserlistOwners() {
        TaskType taskType = TaskType.FETCH_USERLIST_OWNERS;
        return send(taskType);
    }

    @Override
    public Task startGarbageCollection() {
        return null;
    }

    private Task send(TaskType taskType){
        TaskSendType taskSendType = TaskSendType.FIRE_AND_FORGET;
        String msg = "START Task "+taskType+" via MQ by "+ taskSendType;
        log.info(msg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, taskType, taskSendType, countedEntities);

        Message<TaskMessage> mqMessage = taskMessageBuilder.buildTaskMessage(task);

        MessagingTemplate mqTemplate = new MessagingTemplate();
        mqTemplate.send(executorChannelForAsyncStart, mqMessage);
        return task;
    }

    @Autowired
    public AsyncStartTaskImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, ExecutorChannel executorChannelForAsyncStart, TaskMessageBuilder taskMessageBuilder) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.executorChannelForAsyncStart = executorChannelForAsyncStart;
        this.taskMessageBuilder = taskMessageBuilder;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final ExecutorChannel executorChannelForAsyncStart;

    private final TaskMessageBuilder taskMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskImpl.class);
}
