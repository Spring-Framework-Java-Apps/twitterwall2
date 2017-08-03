package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.channel.SenderType;
import org.woehlke.twitterwall.scheduled.mq.endpoint.TweetFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqTweetFinisher")
public class TweetFinisherImpl implements TweetFinisher {

    @Override
    public TweetResultList finish(Message<List<TweetMessage>> incomingMessageList){
        List<Tweet> resultList = new ArrayList<>();
        long taskId = 0L;
        for(TweetMessage msg:incomingMessageList.getPayload()){
            resultList.add(msg.getTweet());
            taskId = msg.getTaskMessage().getTaskId();
        }
        TweetResultList result = new TweetResultList(taskId,resultList);
        return result;
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
        String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER;
        taskService.done(msgDone,task,countedEntities);
        log.info(msgDone);
    }

    @Autowired
    public TweetFinisherImpl(TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private static final Logger log = LoggerFactory.getLogger(UserFinisherImpl.class);
}
