package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.AsyncStartTask;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;

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
        TaskType taskType = TaskType.UPDATE_USERS_FROM_MENTIONS;
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
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_TWEETS;
        return send(taskType);
    }

    @Override
    public Task createTestDataForUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_USERS;
        return send(taskType);
    }

    @Override
    public Task removeOldDataFromStorage() {
        TaskType taskType = TaskType.REMOVE_OLD_DATA_FROM_STORAGE;
        return send(taskType);
    }

    private Task send(TaskType taskType){
        SendType sendType = SendType.FIRE_AND_FORGET;
        String msg = "START Task "+taskType+" via MQ by "+sendType;
        log.info(msg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, taskType, sendType, countedEntities);

        Message<TaskMessage> mqMessage = twitterwallMessageBuilder.buildTaskMessage(task);

        MessagingTemplate mqTemplate = new MessagingTemplate();
        mqTemplate.send(executorChannelForAsyncStart, mqMessage);
        return task;
    }

    @Autowired
    public AsyncStartTaskImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, ExecutorChannel executorChannelForAsyncStart, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.executorChannelForAsyncStart = executorChannelForAsyncStart;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final ExecutorChannel executorChannelForAsyncStart;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskImpl.class);
}
