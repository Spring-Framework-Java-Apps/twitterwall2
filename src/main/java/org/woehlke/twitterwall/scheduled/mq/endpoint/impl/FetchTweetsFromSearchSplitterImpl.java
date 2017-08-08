package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.FetchTweetsFromSearchSplitter;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchTweetsFromTwitterSearchSplitter")
public class FetchTweetsFromSearchSplitterImpl implements FetchTweetsFromSearchSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public FetchTweetsFromSearchSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<TweetMessage>> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<Tweet> twitterTweets = twitterApiService.findTweetsForSearchQuery();
        int loopId = 0;
        int loopAll = twitterTweets.size();
        for (Tweet tweet: twitterTweets) {
            loopId++;
            Message<TweetMessage> mqMessageOut = twitterwallMessageBuilder.buildTweetMessage(message,tweet,loopId,loopAll);
            tweets.add(mqMessageOut);
        }
        return tweets;
    }
}
