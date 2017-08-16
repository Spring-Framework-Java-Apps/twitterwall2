package org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tweets.UpdateTweetsSplitter;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.TWELVE_HOURS;
import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateTweetsSplitter")
public class UpdateTweetsSplitterImpl implements UpdateTweetsSplitter {

    private final TwitterProperties twitterProperties;

    private final TweetService tweetService;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    public UpdateTweetsSplitterImpl(TwitterProperties twitterProperties, TweetService tweetService, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterProperties = twitterProperties;
        this.tweetService = tweetService;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public List<Message<TweetMessage>> splitTweetMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long taskId = msgIn.getTaskId();
        Task task = taskService.findById(taskId);
        task =  taskService.start(task,countedEntities);
        List<Long> worklistTwitterIds = new ArrayList<>();
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        int lfdNr = 0;
        int all = 0;
        while(hasNext) {
            Page<org.woehlke.twitterwall.oodm.entities.Tweet> tweetTwitterIds = tweetService.getAll(pageRequest);
            for(org.woehlke.twitterwall.oodm.entities.Tweet tweetTwitterId:tweetTwitterIds.getContent()){
                if(!tweetTwitterId.getTaskBasedCaching().isCached(task.getTaskType(), TWELVE_HOURS)) {
                    lfdNr++;
                    all++;
                    log.debug("### tweetService.findAllTwitterIds from DB (" + lfdNr + "): " + tweetTwitterId.getIdTwitter());
                    worklistTwitterIds.add(tweetTwitterId.getIdTwitter());
                }
            }
            hasNext = tweetTwitterIds.hasNext();
            pageRequest = pageRequest.next();
        }
        List<Message<TweetMessage>> tweets = new ArrayList<>();
        lfdNr = 0;
        for(Long tweetTwitterId : worklistTwitterIds){
            lfdNr++;
            log.debug("### twitterApiService.findOneTweetById from Twiiter API ("+lfdNr+" of "+all+"): "+tweetTwitterId);
            Tweet foundTweetFromTwitter = twitterApiService.findOneTweetById(tweetTwitterId);
            TweetMessage result = new TweetMessage(msgIn,foundTweetFromTwitter);
            Message<TweetMessage> mqMessageOut = twitterwallMessageBuilder.buildTweetMessage(incomingTaskMessage,foundTweetFromTwitter,lfdNr,all);
            tweets.add(mqMessageOut);
            twitterwallMessageBuilder.waitForApi();
        }
        return tweets;
    }

    private static final Logger log = LoggerFactory.getLogger(UpdateTweetsSplitterImpl.class);

}
