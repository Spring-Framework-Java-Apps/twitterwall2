package org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.impl;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.configuration.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.backend.mq.users.endpoint.splitter.FetchUsersFromListSplitter;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.users.msg.UserMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchUserFromListSplitter")
public class FetchUsersFromListSplitterImpl implements FetchUsersFromListSplitter {

    private final SchedulerProperties schedulerProperties;

    private final FrontendProperties frontendProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserMessageBuilder userMessageBuilder;

    public FetchUsersFromListSplitterImpl(SchedulerProperties schedulerProperties, FrontendProperties frontendProperties, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService, UserMessageBuilder userMessageBuilder) {
        this.schedulerProperties = schedulerProperties;
        this.frontendProperties = frontendProperties;
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
        String imprintScreenName = frontendProperties.getImprintScreenName();
        String fetchUsersList = schedulerProperties.getFetchUsersFromDefinedUserListName();
        List<TwitterProfile> foundTwitterProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUsersList);
        int loopId = 0;
        int loopAll = foundTwitterProfiles.size();
        for (TwitterProfile twitterProfile : foundTwitterProfiles) {
            loopId++;
            Message<UserMessage> mqMessageOut = userMessageBuilder.buildUserMessage(incomingTaskMessage,twitterProfile,loopId,loopAll);
            userProfileList.add(mqMessageOut);
        }
        return userProfileList;
    }
}
