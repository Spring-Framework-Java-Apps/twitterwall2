package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.FetchUsersFromListSplitter;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqFetchUserFromListSplitter")
public class FetchUsersFromListSplitterImpl implements FetchUsersFromListSplitter {

    private final SchedulerProperties schedulerProperties;

    private final FrontendProperties frontendProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    public FetchUsersFromListSplitterImpl(SchedulerProperties schedulerProperties, FrontendProperties frontendProperties, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.schedulerProperties = schedulerProperties;
        this.frontendProperties = frontendProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<Message<UserMessage>> splitMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserMessage>> userProfileList = new ArrayList<>();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        String imprintScreenName = frontendProperties.getImprintScreenName();
        String fetchUsersList = schedulerProperties.getFetchUserList().getName();
        List<TwitterProfile> foundTwitterProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUsersList);
        int loopId = 0;
        int loopAll = foundTwitterProfiles.size();
        for (TwitterProfile twitterProfile : foundTwitterProfiles) {
            loopId++;
            UserMessage userMsg = new UserMessage(msgIn,twitterProfile);
            Message<UserMessage> mqMessageOut =
                    MessageBuilder.withPayload(userMsg)
                            .copyHeaders(incomingTaskMessage.getHeaders())
                            .setHeader("tw_lfd_nr",loopId)
                            .setHeader("tw_all",loopAll)
                            .build();
            userProfileList.add(mqMessageOut);
        }
        return userProfileList;
    }
}
