package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.FetchTweetsFromTwitterSearch;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchTweetsFromTwitterSearch")
public class FetchTweetsFromTwitterSearchImpl implements FetchTweetsFromTwitterSearch {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    @Autowired
    public FetchTweetsFromTwitterSearchImpl(TwitterApiService twitterApiService, TaskService taskService) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
    }

    @Override
    public List<TweetFromTwitter> splitMessage(Message<TaskMessage> message) {
        List<TweetFromTwitter> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task);
        List<Tweet> twitterTweets = twitterApiService.findTweetsForSearchQuery();
        for (Tweet tweet: twitterTweets) {
            TweetFromTwitter tweetMsg = new TweetFromTwitter(task.getId(),tweet);
            tweets.add(tweetMsg);
        }
        return tweets;
    }
}
