package org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.splitter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.endpoint.users.endpoint.splitter.FetchFriendsSplitter;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.endpoint.users.msg.UserMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchFriendsSplitter")
public class FetchFriendsSplitterImpl implements FetchFriendsSplitter {

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserMessageBuilder userMessageBuilder;

    @Autowired
    public FetchFriendsSplitterImpl(TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, UserMessageBuilder userMessageBuilder) {
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
        CursoredList<Long> foundUserProfiles = twitterApiService.getFriendIds();
        int loopId = 0;
        int loopAll = foundUserProfiles.size();
        for (Long twitterUserId : foundUserProfiles) {
            loopId++;
            Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingTaskMessage,twitterUserId,loopId,loopAll);
            userProfileList.add(mqMessageOut);
        }
        return userProfileList;
    }
}
