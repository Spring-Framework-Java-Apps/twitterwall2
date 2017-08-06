package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.CreateTestDataTweetsSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.TWELVE_HOURS;

@Component("mqCreateTestDataForTweetsSplitter")
public class CreateTestDataTweetsSplitterImpl implements CreateTestDataTweetsSplitter {

    private final TestdataProperties testdataProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final TweetService tweetService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public CreateTestDataTweetsSplitterImpl(TestdataProperties testdataProperties, TwitterApiService twitterApiService, TaskService taskService, TweetService tweetService, CountedEntitiesService countedEntitiesService) {
        this.testdataProperties = testdataProperties;
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
        List<Long> userIdTwitterList =  testdataProperties.getOodm().getEntities().getTweet().getIdTwitter();
        int loopId = 0;
        int loopAll = userIdTwitterList.size();
        for (long idTwitter : userIdTwitterList) {
            loopId++;
            org.woehlke.twitterwall.oodm.entities.Tweet tweetPers = tweetService.findByIdTwitter(idTwitter);
            if(tweetPers == null){
                tweets.add(fetchTweetFromTwitter(idTwitter,task,incomingTaskMessage,loopId,loopAll));
            } else {
                if(tweetPers.getTwitterApiCaching().isCached(task.getTaskType(), TWELVE_HOURS)) {
                    TweetMessage msg = new TweetMessage(msgIn,tweetPers);
                    Message<TweetMessage> mqMessageOut =
                            MessageBuilder.withPayload(msg)
                                    .copyHeaders(incomingTaskMessage.getHeaders())
                                    .setHeader("tw_lfd_nr",loopId)
                                    .setHeader("tw_all",loopAll)
                                    .build();
                    tweets.add(mqMessageOut);
                } else {
                    tweets.add(fetchTweetFromTwitter(idTwitter,task,incomingTaskMessage,loopId,loopAll));
                }
            }
        }
        return tweets;
    }

    private Message<TweetMessage> fetchTweetFromTwitter(long idTwitter, Task task, Message<TaskMessage> incomingTaskMessage,int loopId,int loopAll){
        Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
        TweetMessage tweetMsg = new TweetMessage(incomingTaskMessage.getPayload(),tweet);
        Message<TweetMessage> mqMessageOut =
                MessageBuilder.withPayload(tweetMsg)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .setHeader("tw_lfd_nr",loopId)
                        .setHeader("tw_all",loopAll)
                        .build();
        return mqMessageOut;
    }
}
