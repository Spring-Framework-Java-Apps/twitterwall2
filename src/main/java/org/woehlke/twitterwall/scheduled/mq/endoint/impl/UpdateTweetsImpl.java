package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.mq.endoint.UpdateTweets;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetFromTwitter;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateTweets")
public class UpdateTweetsImpl implements UpdateTweets {

    private final TwitterProperties twitterProperties;

    private final TweetService tweetService;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    public UpdateTweetsImpl(TwitterProperties twitterProperties, TweetService tweetService, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.twitterProperties = twitterProperties;
        this.tweetService = tweetService;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<TweetFromTwitter> splitMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<TweetFromTwitter> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long taskId = msgIn.getTaskId();
        Task task = taskService.findById(taskId);
        task =  taskService.start(task,countedEntities);
        List<Long> worklistTwitterIds = new ArrayList<>();
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while(hasNext) {
            Page<Long> tweetTwitterIds = tweetService.findAllTwitterIds(pageRequest);
            worklistTwitterIds.addAll(tweetTwitterIds.getContent());
            hasNext = tweetTwitterIds.hasNext();
            pageRequest = pageRequest.next();
        }
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        for(Long tweetTwitterId : worklistTwitterIds){
            Tweet foundTweetFromTwitter = twitterApiService.findOneTweetById(tweetTwitterId);
            TweetFromTwitter result = new TweetFromTwitter(task.getId(),foundTweetFromTwitter);
            tweets.add(result);
            try {
                Thread.sleep(millisToWaitBetweenTwoApiCalls);
            } catch (InterruptedException e) {
            }
        }
        return tweets;
    }
}
