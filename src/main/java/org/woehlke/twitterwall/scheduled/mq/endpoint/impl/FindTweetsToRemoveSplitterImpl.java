package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.FindTweetsToRemoveSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TweetMessage;

import java.util.ArrayList;
import java.util.List;

@Component("mqFindTweetsToRemoveSplitter")
public class FindTweetsToRemoveSplitterImpl implements FindTweetsToRemoveSplitter {

    private final TweetService tweetService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public FindTweetsToRemoveSplitterImpl(TweetService tweetService, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.tweetService = tweetService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<Message<TweetMessage>> splitMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<TweetMessage>> tweets = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        int pageTweet = 1;
        int pageSize = 20;
        Pageable pageRequestTweet = new PageRequest(pageTweet, pageSize);
        //TODO: #229 https://github.com/phasenraum2010/twitterwall2/issues/229
        Page<Tweet> tweetList = tweetService.getAll(pageRequestTweet);
        int loopId = 0;
        int loopAll = tweetList.getContent().size();
        for (Tweet tweet: tweetList) {
            loopId++;
            TweetMessage tweetMsg = new TweetMessage(msgIn,tweet);
            Message<TweetMessage> mqMessageOut =
                    MessageBuilder.withPayload(tweetMsg)
                            .copyHeaders(message.getHeaders())
                            .setHeader("tw_lfd_nr",loopId)
                            .setHeader("tw_all",loopAll)
                            .build();
            tweets.add(mqMessageOut);
        }
        return tweets;
    }
}
