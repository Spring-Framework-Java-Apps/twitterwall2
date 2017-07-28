package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.CreateTestDataForUsers;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

@Component("mqCreateTestDataForUsers")
public class CreateTestDataForUsersImpl implements CreateTestDataForUsers {

    private final SchedulerProperties schedulerProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final CountedEntitiesService countedEntitiesService;

    public CreateTestDataForUsersImpl(SchedulerProperties schedulerProperties, TwitterApiService twitterApiService, TaskService taskService, CountedEntitiesService countedEntitiesService) {
        this.schedulerProperties = schedulerProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<TwitterProfileMessage> userProfileList = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<String> userIdList = schedulerProperties.getFacade().getScreenNamesToFetchForUserControllerTest();
        for (String screenName : userIdList) {
            TwitterProfile userProfile = twitterApiService.getUserProfileForScreenName(screenName);
            TwitterProfileMessage userMsg = new TwitterProfileMessage(msgIn,userProfile);
            userProfileList.add(userMsg);
        }
        return userProfileList;
    }
}
