package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.CreateTestDataForUsers;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.ZWOELF_STUNDEN;

@Component("mqCreateTestDataForUsers")
public class CreateTestDataForUsersImpl implements CreateTestDataForUsers {

    private final SchedulerProperties schedulerProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    public CreateTestDataForUsersImpl(SchedulerProperties schedulerProperties, TwitterApiService twitterApiService, TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService) {
        this.schedulerProperties = schedulerProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userService = userService;
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
        List<String> userIdList = schedulerProperties.getFacade().getScreenNamesToFetchForUserControllerTest();
        for (String screenName : userIdList) {
            User userPers = userService.findByScreenName(screenName);
            if(userPers==null){
                userProfileList.add(getUserProfileFromTwitterApi(incomingTaskMessage,screenName));
            } else {
                if(!userPers.getTwitterApiCaching().isCached(task.getTaskType(),ZWOELF_STUNDEN)) {
                    userProfileList.add(getUserProfileFromTwitterApi(incomingTaskMessage,screenName));
                } else {
                    UserMessage msg = new UserMessage(msgIn,screenName,userPers);
                    Message<UserMessage> mqMessageOut =
                            MessageBuilder.withPayload(msg)
                                    .copyHeaders(incomingTaskMessage.getHeaders())
                                    .build();
                    userProfileList.add(mqMessageOut);
                }
            }
        }
        return userProfileList;
    }

    private Message<UserMessage> getUserProfileFromTwitterApi(Message<TaskMessage> incomingTaskMessage, String screenName){
        TwitterProfile userProfile = twitterApiService.getUserProfileForScreenName(screenName);
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        UserMessage msg = new UserMessage(msgIn,userProfile);
        Message<UserMessage> mqMessageOut =
                MessageBuilder.withPayload(msg)
                        .copyHeaders(incomingTaskMessage.getHeaders())
                        .build();
        return mqMessageOut;

    }
}
