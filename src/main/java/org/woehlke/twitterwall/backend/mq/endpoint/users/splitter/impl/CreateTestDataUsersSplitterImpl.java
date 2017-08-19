package org.woehlke.twitterwall.backend.mq.endpoint.users.splitter.impl;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.backend.mq.endpoint.users.splitter.CreateTestDataUsersSplitter;
import org.woehlke.twitterwall.backend.mq.msg.TaskMessage;
import org.woehlke.twitterwall.backend.mq.msg.UserMessage;
import org.woehlke.twitterwall.backend.mq.msg.builder.UserMessageBuilder;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.TWELVE_HOURS;

@Component("mqCreateTestDataForUsersSplitter")
public class CreateTestDataUsersSplitterImpl implements CreateTestDataUsersSplitter {

    private final TestdataProperties testdataProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final UserMessageBuilder userMessageBuilder;

    public CreateTestDataUsersSplitterImpl(TestdataProperties testdataProperties, TwitterApiService twitterApiService, TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService, UserMessageBuilder userMessageBuilder) {
        this.testdataProperties = testdataProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.userMessageBuilder = userMessageBuilder;
    }

    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<Message<UserMessage>> userProfileList = new ArrayList<>();
        TaskMessage incomingTaskMessagePayload = incomingTaskMessage.getPayload();
        long id = incomingTaskMessagePayload.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<String> listScreenName = testdataProperties.getOodm().getEntities().getUser().getScreenName();
        int loopId = 0;
        int loopAll = listScreenName.size();
        for (String screenName : listScreenName) {
            loopId++;
            boolean fetchFromTwitterApi=true;
            User userPers = userService.findByScreenName(screenName);
            if(userPers==null){
                fetchFromTwitterApi=true;
            } else {
                fetchFromTwitterApi=!userPers.getTaskBasedCaching().isCached(task.getTaskType(), TWELVE_HOURS);
                userPers = userService.store(userPers,task);
            }
            Message<UserMessage> outgoingMessage;
            if(fetchFromTwitterApi){
                TwitterProfile userProfile = twitterApiService.getUserProfileForScreenName(screenName);
                outgoingMessage = userMessageBuilder.buildUserMessage(incomingTaskMessage,userProfile,loopId,loopAll);
            } else {
                outgoingMessage = userMessageBuilder.buildUserMessage(incomingTaskMessage,userPers,loopId,loopAll);
            }
            userProfileList.add(outgoingMessage);
        }
        return userProfileList;
    }


}
