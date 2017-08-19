package org.woehlke.twitterwall.scheduled.mq.endpoint.users.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.splitter.FetchFollowerSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.builder.UserMessageBuilder;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchFollowerSplitter")
public class FetchFollowerSplitterImpl implements FetchFollowerSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public FetchFollowerSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, UserMessageBuilder userMessageBuilder) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
        this.userMessageBuilder = userMessageBuilder;
    }

    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserMessage>> userProfileList = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        CursoredList<Long> foundTwitterProfiles = twitterApiService.getFollowerIds();
        int loopId = 0;
        int loopAll = foundTwitterProfiles.size();
        for (Long twitterProfileId : foundTwitterProfiles) {
            loopId++;
            Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingTaskMessage,twitterProfileId,loopId,loopAll);
            userProfileList.add(mqMessageOut);
        }
        return userProfileList;
    }
}
