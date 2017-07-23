package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.CreateTestDataForTweets;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqCreateTestDataForTweets")
public class CreateTestDataForTweetsImpl implements CreateTestDataForTweets {

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    @Autowired
    public CreateTestDataForTweetsImpl(TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterApiService twitterApiService, TaskService taskService) {
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
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
        for (long idTwitter : twitterwallSchedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()) {
            Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
            TweetFromTwitter tweetMsg = new TweetFromTwitter(task.getId(),tweet);
            tweets.add(tweetMsg);
        }
        return tweets;
    }
}
