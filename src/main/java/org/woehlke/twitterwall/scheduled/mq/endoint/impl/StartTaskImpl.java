package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartTaskImpl implements StartTask {

    @Override
    public void fetchTweetsFromTwitterSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        sendAndReceiveTweet(taskType);
    }

    @Override
    public void updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        sendAndReceiveTweet(taskType);
    }

    @Override
    public void updateUserProfiles() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES;
        sendAndReceiveUser(taskType);
    }

    @Override
    public void updateUserProfilesFromMentions() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS;
        sendAndReceiveUser(taskType);
    }

    @Override
    public void fetchUsersFromDefinedUserList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST;
        sendAndReceiveUser(taskType);
    }

    @Override
    public User createImprintUser() {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskType taskType = TaskType.CONTROLLER_CREATE_IMPRINT_USER;
        Task task = taskService.create("Start via MQ", taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
            .setHeader("task_id", task.getId())
            .setHeader("task_uid", task.getUniqueId())
            .setHeader("task_type", task.getTaskType())
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task,countedEntities);
            return msg.getUser();
        } else {
            taskService.error(task,"Wrong type of returnedMessage",countedEntities);
            return null;
        }
    }

    @Override
    public List<User> createTestDataForUser() {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskType taskType = TaskType.CONTROLLER_GET_TESTDATA_USER;
        Task task = taskService.create("Start via MQ", taskType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if (o instanceof UserResultList) {
            UserResultList result = (UserResultList) o;
            long taskId = result.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task, countedEntities);
            return result.getUserList();
        } else {
            taskService.error(task, "Wrong type of returnedMessage", countedEntities);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tweet> createTestDataForTweets() {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskType taskType = TaskType.CONTROLLER_GET_TESTDATA_TWEETS;
        Task task = taskService.create("Start via MQ", taskType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if (o instanceof TweetResultList) {
            TweetResultList result = (TweetResultList) o;
            long taskId = result.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task, countedEntities);
            return result.getTweetList();
        } else {
            taskService.error(task, "Wrong type of returnedMessage", countedEntities);
            return new ArrayList<>();
        }
    }

    private void sendAndReceiveTweet(TaskType taskType){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TweetResultList){
            TweetResultList msg = (TweetResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task,countedEntities);
        } else {
            taskService.error(task,"Wrong type of returnedMessage",countedEntities);
        }
    }

    private void sendAndReceiveUser(TaskType taskType){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task,countedEntities);
        } else {
            taskService.error(task,"Wrong type of returnedMessage",countedEntities);
        }
    }

    private final MessageChannel startTaskChannel;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public StartTaskImpl(MessageChannel startTaskChannel, TaskService taskService, CountedEntitiesService countedEntitiesService, TwitterwallSchedulerProperties twitterwallSchedulerProperties) {
        this.startTaskChannel = startTaskChannel;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }
}


