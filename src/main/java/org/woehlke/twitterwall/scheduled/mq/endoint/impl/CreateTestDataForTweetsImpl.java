package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.CreateTestDataForTweets;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqCreateTestDataForTweets")
public class CreateTestDataForTweetsImpl implements CreateTestDataForTweets {

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public CreateTestDataForTweetsImpl(TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<TweetFromTwitter> splitMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<TweetFromTwitter> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        for (long idTwitter : twitterwallSchedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()) {
            Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
            TweetFromTwitter tweetMsg = new TweetFromTwitter(task.getId(),tweet);
            tweets.add(tweetMsg);
        }
        return tweets;
    }
}
