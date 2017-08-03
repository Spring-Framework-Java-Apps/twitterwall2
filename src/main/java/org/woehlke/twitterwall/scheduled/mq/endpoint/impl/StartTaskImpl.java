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
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.StartTask;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserResultList;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqStartTask")
public class StartTaskImpl implements StartTask {

    @Override
    public Task fetchTweetsFromTwitterSearch() {
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task updateTweets() {
        TaskType taskType = TaskType.UPDATE_TWEETS;
        return sendAndReceiveTweet(taskType);
    }

    @Override
    public Task updateUserProfiles() {
        TaskType taskType = TaskType.UPDATE_USERS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task updateUserProfilesFromMentions() {
        TaskType taskType = TaskType.UPDATE_USERS_FROM_MENTIONS;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public Task fetchUsersFromDefinedUserList() {
        TaskType taskType = TaskType.FETCH_USERS_FROM_LIST;
        return sendAndReceiveUser(taskType);
    }

    @Override
    public User createImprintUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_IMPRINT_USER;
        String logMsg = "Start task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", "synchron")
            .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
            taskService.done(logMsg, task, countedEntities);
            log.info(logMsg);
            return msg.getUser();
        } else {
            logMsg = "Finished with Error: task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER: Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
            return null;
        }
    }

    @Override
    public List<User> createTestDataForUser() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_USERS;
        String logMsg = "Start task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ", taskType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", "synchron")
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if (o instanceof UserResultList) {
            UserResultList result = (UserResultList) o;
            long taskId = result.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
            taskService.done(logMsg, task, countedEntities);
            log.info(logMsg);
            return result.getUserList();
        } else {
            logMsg = "Finished with Error: task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER: Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tweet> createTestDataForTweets() {
        TaskType taskType = TaskType.CONTROLLER_CREATE_TESTDATA_TWEETS;
        String logMsg = "Start task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType, countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", "synchron")
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if (o instanceof TweetResultList) {
            TweetResultList result = (TweetResultList) o;
            long taskId = result.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
            taskService.done(logMsg, task, countedEntities);
            log.info(logMsg);
            return result.getTweetList();
        } else {
            logMsg = "Finished with Error: task "+taskType+" via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER: Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
            return new ArrayList<>();
        }
    }

    private Task sendAndReceiveTweet(TaskType taskType){
        String logMsg = "Start task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create("Start via MQ by Scheduler ", taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", "synchron")
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof TweetResultList){
            TweetResultList msg = (TweetResultList) o;
            long taskId = msg.getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
            taskService.done(logMsg,task,countedEntities);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER: Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private Task sendAndReceiveUser(TaskType taskType){
        String logMsg = "Start task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
        log.info(logMsg);
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(logMsg, taskType,countedEntities);
        TaskMessage taskMessage = new TaskMessage(task.getId(), taskType, task.getTimeStarted());
        Message<TaskMessage> mqMessage = MessageBuilder.withPayload(taskMessage)
                .setHeader("task_id", task.getId())
                .setHeader("task_uid", task.getUniqueId())
                .setHeader("task_type", task.getTaskType())
                .setHeader("time_started", task.getTimeStarted().getTime())
                .setHeader("send_type", "synchron")
                .build();
        MessagingTemplate mqTemplate = new MessagingTemplate();
        Message<?> returnedMessage = mqTemplate.sendAndReceive(startTaskChannel, mqMessage);
        Object o = returnedMessage.getPayload();
        countedEntities = countedEntitiesService.countAll();
        if( o instanceof UserMessage){
            UserMessage msg = (UserMessage) o;
            long taskId = msg.getTaskMessage().getTaskId();
            task = taskService.findById(taskId);
            logMsg = "Sucessfully finished task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER";
            taskService.done(logMsg,task,countedEntities);
            log.info(logMsg);
        } else {
            logMsg = "Finished with Error: task "+taskType+"via MQ by SEND_AND_WAIT_FOR_RESULT_SENDER: Wrong type of returnedMessage";
            taskService.finalError(task,logMsg,countedEntities);
            log.error(logMsg);
        }
        return task;
    }

    private final MessageChannel startTaskChannel;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private static final Logger log = LoggerFactory.getLogger(StartTaskImpl.class);

    @Autowired
    public StartTaskImpl(MessageChannel startTaskChannel,TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.startTaskChannel = startTaskChannel;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

}


