package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.CreateTestDataForTweets;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.ZWOELF_STUNDEN;

@Component("mqCreateTestDataForTweets")
public class CreateTestDataForTweetsImpl implements CreateTestDataForTweets {

    private final SchedulerProperties schedulerProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final TweetService tweetService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public CreateTestDataForTweetsImpl(SchedulerProperties schedulerProperties, TwitterApiService twitterApiService, TaskService taskService, TweetService tweetService, CountedEntitiesService countedEntitiesService) {
        this.schedulerProperties = schedulerProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.tweetService = tweetService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<Message<TweetMessage>> splitMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<TweetMessage>> tweets = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        for (long idTwitter : schedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()) {
            org.woehlke.twitterwall.oodm.entities.Tweet tweetPers = tweetService.findByIdTwitter(idTwitter);
            if(tweetPers == null){
                tweets.add(getTweetFromTwitter(idTwitter,task,incomingTaskMessage));
            } else {
                if(tweetPers.getTwitterApiCaching().isCached(task.getTaskType(),ZWOELF_STUNDEN)) {
                    TweetMessage msg = new TweetMessage(msgIn,tweetPers);
                    Message<TweetMessage> mqMessageOut =
                            MessageBuilder.withPayload(msg)
                                    .copyHeaders(incomingTaskMessage.getHeaders())
                                    .build();
                    tweets.add(mqMessageOut);
                } else {
                    tweets.add(getTweetFromTwitter(idTwitter,task,incomingTaskMessage));
                }
            }
        }
        return tweets;
    }

    private Message<TweetMessage> getTweetFromTwitter(long idTwitter, Task task, Message<TaskMessage> incomingTaskMessage){
        Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
        TweetMessage tweetMsg = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(tweetMsg)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .build();
        return mqMessageOut;
    }
}
