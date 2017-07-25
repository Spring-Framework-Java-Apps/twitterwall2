package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.channel.SenderType;
import org.woehlke.twitterwall.scheduled.mq.endoint.AsyncStartTask;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

@Component("mqAsyncStartTask")
public class AsyncStartTaskImpl implements AsyncStartTask {

    @Override
    public void fetchTweetsFromTwitterSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        send(taskType);
    }

    @Override
    public void updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        send(taskType);
    }

    @Override
    public void updateUserProfiles() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES;
        send(taskType);
    }

    @Override
    public void updateUserProfilesFromMentions() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS;
        send(taskType);
    }

    @Override
    public void fetchUsersFromDefinedUserList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST;
        send(taskType);
    }

    private void send(TaskType taskType){
        String msg = "Start task "+taskType+"via MQ by "+SenderType.FIRE_AND_FORGET_SENDER;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        log.info(msg);
        mqTemplate.send(executorChannelForAsyncStart, mqMessage);
    }

    @Autowired
    public AsyncStartTaskImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, ExecutorChannel executorChannelForAsyncStart) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.executorChannelForAsyncStart = executorChannelForAsyncStart;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final ExecutorChannel executorChannelForAsyncStart;

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskImpl.class);
}
