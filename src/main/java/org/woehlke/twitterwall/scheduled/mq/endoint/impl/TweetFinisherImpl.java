package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

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
import org.woehlke.twitterwall.scheduled.mq.endoint.TweetFinisher;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetResultList;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqTweetFinisher")
public class TweetFinisherImpl implements TweetFinisher {

    @Override
    public TweetResultList finish(Message<List<TweetFromTwitter>> incomingMessageList){
        List<Tweet> resultList = new ArrayList<>();
        long taskId = 0L;
        for(TweetFromTwitter msg:incomingMessageList.getPayload()){
            resultList.add(msg.getTweet());
            taskId = msg.getTaskId();
        }
        TweetResultList result = new TweetResultList(taskId,resultList);
       return result;
   }

    @Override
    public void finishAsnyc(Message<List<TweetFromTwitter>> incomingMessageList) {
        List<TweetFromTwitter> userMessageList = incomingMessageList.getPayload();
        CountedEntities countedEntities = countedEntitiesService.countAll();
        if(incomingMessageList.getHeaders().containsKey("task_id")){
            long taskId = (Long) incomingMessageList.getHeaders().get( "task_id");
            Task task = taskService.findById(taskId);
            String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER;
            taskService.done(msgDone,task,countedEntities);
            log.info(msgDone);
        } else {
            if(userMessageList.size()>0) {
                long taskId = userMessageList.get(0).getTaskId();
                Task task = taskService.findById(taskId);
                String msgDone = "Sucessfully finished task "+task.getTaskType()+" via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER;
                taskService.done(task,countedEntities);
                log.info(msgDone);
            } else {
                log.warn("finishAsnyc: No Tweets performed. via MQ by "+ SenderType.FIRE_AND_FORGET_SENDER);
            }
        }
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
