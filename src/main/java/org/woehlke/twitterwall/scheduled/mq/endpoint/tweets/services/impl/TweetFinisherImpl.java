package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.services.impl.UserFinisherImpl;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.services.TweetFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.TweetMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.results.TweetResultList;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqTweetFinisher")
public class TweetFinisherImpl implements TweetFinisher {

    @Override
    public Message<TweetResultList> finish(Message<List<TweetMessage>> incomingMessageList){
        List<Tweet> resultList = new ArrayList<>();
        long taskId = 0L;
        for(TweetMessage msg:incomingMessageList.getPayload()){
            resultList.add( msg.getTweet());
            taskId = msg.getTaskMessage().getTaskId();
        }
        TweetResultList result = new TweetResultList(taskId,resultList);
        Message<TweetResultList> mqMessageOut = MessageBuilder.withPayload(result)
                .copyHeaders(incomingMessageList.getHeaders())
                .setHeader("persisted",Boolean.TRUE)
                .build();
        return mqMessageOut;
    }

    @Override
    public void finishAsnyc(Message<List<TweetMessage>> incomingMessageList) {
        List<TweetMessage> userMessageList = incomingMessageList.getPayload();
        CountedEntities countedEntities = countedEntitiesService.countAll();
        long taskId = 0L;
        for(TweetMessage msg:incomingMessageList.getPayload()){
            taskId = msg.getTaskMessage().getTaskId();
            break;
        }
        Task task = taskService.findById(taskId);
        String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by FIRE_AND_FORGET_SENDER";
        taskService.done(msgDone,task,countedEntities);
        log.info(msgDone);
    }

    @Autowired
    public TweetFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService, TweetMessageBuilder tweetMessageBuilder) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.tweetMessageBuilder = tweetMessageBuilder;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TweetMessageBuilder tweetMessageBuilder;

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
