package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.FetchFriendsSplitter;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchFriendsSplitter")
public class FetchFriendsSplitterImpl implements FetchFriendsSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public FetchFriendsSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserMessage>> userProfileList = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        CursoredList<Long> foundUserProfiles = twitterApiService.getFriendIds();
        int loopId = 0;
        int loopAll = foundUserProfiles.size();
        for (Long twitterUserId : foundUserProfiles) {
            loopId++;
            Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(incomingTaskMessage,twitterUserId,loopId,loopAll);
            userProfileList.add(mqMessageOut);
        }
        return userProfileList;
    }
}
