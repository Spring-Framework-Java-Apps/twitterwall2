package org.woehlke.twitterwall.backend.mq.tweets.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.backend.mq.tweets.endpoint.splitter.CreateTestDataTweetsSplitter;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.CronJobs.TWELVE_HOURS;

@Component("mqCreateTestDataForTweetsSplitter")
public class CreateTestDataTweetsSplitterImpl implements CreateTestDataTweetsSplitter {

    private final TestdataProperties testdataProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final TweetService tweetService;

    private final CountedEntitiesService countedEntitiesService;

    private final TweetMessageBuilder tweetMessageBuilder;

    @Autowired
    public CreateTestDataTweetsSplitterImpl(TestdataProperties testdataProperties, TwitterApiService twitterApiService, TaskService taskService, TweetService tweetService, CountedEntitiesService countedEntitiesService, TweetMessageBuilder tweetMessageBuilder) {
        this.testdataProperties = testdataProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.tweetService = tweetService;
        this.countedEntitiesService = countedEntitiesService;
        this.tweetMessageBuilder = tweetMessageBuilder;
    }

    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> incomingTaskMessage) {
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
            boolean fetchTweetFromApi = true;
            org.woehlke.twitterwall.oodm.model.Tweet tweetPers = tweetService.findByIdTwitter(idTwitter);
            if(tweetPers == null){
                fetchTweetFromApi = true;
            } else {
                fetchTweetFromApi = !tweetPers.getTaskBasedCaching().isCached(task.getTaskType(), TWELVE_HOURS);
            }
            Message<TweetMessage> outgoingMessage = null;
            if(fetchTweetFromApi) {
                Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                outgoingMessage = tweetMessageBuilder.buildTweetMessage(incomingTaskMessage, tweet, loopId, loopAll);
            } else {
                outgoingMessage = tweetMessageBuilder.buildTweetMessage(incomingTaskMessage, tweetPers,loopId,loopAll);
            }
            tweets.add(outgoingMessage);
        }
        return tweets;
    }


}
