package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartTaskImpl implements StartTask {

    private final MessageChannel startTaskChannel;

    private final TaskService taskService;

    @Autowired
    public StartTaskImpl(MessageChannel startTaskChannel, TaskService taskService) {
        this.startTaskChannel = startTaskChannel;
        this.taskService = taskService;
    }

    private void messageSender(final TaskType taskType) {
        Task task = taskService.create("Start via MQ", taskType);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
            .setHeader("task_id", task.getId())
            .setHeader("task_uid", task.getUniqueId())
            .setHeader("task_type", task.getTaskType())
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        mqTemplate.send(startTaskChannel, mqMessage);
    }


    private final static long EINE_MINUTE = 60 * 1000;

    private final static long FUENF_MINUTEN = 5 * EINE_MINUTE;

    private final static long EINE_STUNDE = 60 * EINE_MINUTE;

    private final static long ZWOELF_STUNDEN = 12 * EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST = ZWOELF_STUNDEN;


      /*
    twitterwall.mq.starttask.time.fetchTweetsFromTwitterSearch
    twitterwall.mq.starttask.time.updateTweets
    twitterwall.mq.starttask.time.updateUserProfiles
    twitterwall.mq.starttask.time.updateUserProfilesFromMentions
    twitterwall.mq.starttask.time.fetchUsersFromDefinedUserList
    */


    @Override
    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS)
    public void fetchTweetsFromTwitterSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        messageSender(taskType);
    }

    @Override
    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS)
    public void updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        messageSender(taskType);
    }

    @Override
    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER)
    public void updateUserProfiles() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES;
        messageSender(taskType);
    }

    @Override
    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION)
    public void updateUserProfilesFromMentions() {
        TaskType taskType = TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS;
        messageSender(taskType);
    }

    @Override
    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST)
    public void fetchUsersFromDefinedUserList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST;
        messageSender(taskType);
    }

    @Override
    public User createImprintUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_IMPRINT_USER;
        Task task = taskService.create("Start via MQ", taskType);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
            .setHeader("task_id", task.getId())
            .setHeader("task_uid", task.getUniqueId())
            .setHeader("task_type", task.getTaskType())
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task);
            return msg.getUser();
        } else {
            taskService.error(task,"Wrong type of returnedMessage");
            return null;
        }
    }

    @Override
    public List<User> createTestDataForUser() {
        TaskType taskType = TaskType.CONTROLLER_GET_TESTDATA_USER;
        Task task = taskService.create("Start via MQ", taskType);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
            .setHeader("task_id", task.getId())
            .setHeader("task_uid", task.getUniqueId())
            .setHeader("task_type", task.getTaskType())
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        if( o instanceof UserResultList){
            UserResultList result = (UserResultList) o;
            long taskId = result.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task);
            return result.getUserList();
        } else {
            taskService.error(task,"Wrong type of returnedMessage");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tweet> createTestDataForTweets() {
        TaskType taskType = TaskType.CONTROLLER_GET_TESTDATA_TWEETS;
        Task task = taskService.create("Start via MQ", taskType);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
            .setHeader("task_id", task.getId())
            .setHeader("task_uid", task.getUniqueId())
            .setHeader("task_type", task.getTaskType())
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        if( o instanceof TweetResultList){
            TweetResultList result = (TweetResultList) o;
            long taskId =result.getTaskId();
            task = taskService.findById(taskId);
            taskService.done(task);
            return result.getTweetList();
        } else {
            taskService.error(task,"Wrong type of returnedMessage");
            return new ArrayList<>();
        }
    }
}


