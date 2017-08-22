package org.woehlke.twitterwall.backend.mq.tweets.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.tweets.endpoint.splitter.RetweetsOfMeSplitter;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessage;
import org.woehlke.twitterwall.backend.mq.tweets.msg.TweetMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqRetweetsOfMeSplitter")
public class RetweetsOfMeSplitterImpl implements RetweetsOfMeSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TweetMessageBuilder tweetMessageBuilder;

    @Autowired
    public RetweetsOfMeSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, TweetMessageBuilder tweetMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.tweetMessageBuilder = tweetMessageBuilder;
    }


    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<TweetMessage>> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<Tweet> twitterTweets = twitterApiService.getRetweetsOfMe();
        int loopId = 0;
        int loopAll = twitterTweets.size();
        for (Tweet tweet: twitterTweets) {
            loopId++;
            Message<TweetMessage> mqMessageOut = tweetMessageBuilder.buildTweetMessage(message,tweet,loopId,loopAll);
            tweets.add(mqMessageOut);
        }
        return tweets;
    }
}
