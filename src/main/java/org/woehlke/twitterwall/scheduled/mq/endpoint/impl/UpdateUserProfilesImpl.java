package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.ZWOELF_STUNDEN;
import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateUserProfiles")
public class UpdateUserProfilesImpl implements UpdateUserProfiles {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesImpl.class);

    private final TwitterProperties twitterProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public UpdateUserProfilesImpl(TwitterProperties twitterProperties, TwitterApiService twitterApiService, TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService) {
        this.twitterProperties = twitterProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<UserMessage> splitMessage(Message<TaskMessage> message) {
        String msg = "### mqUpdateUserProfiles.splitMessage: ";
        log.debug(msg+ " START");
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        TaskType taskType = task.getTaskType();
        int loopId = 0;
        boolean hasNext=true;
        List<Long> worklistProfileTwitterIds = new ArrayList<>();
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<User> userProfileTwitterIds = userService.getAll(pageRequest);
            for(User user:userProfileTwitterIds.getContent()){
                if(!user.getTwitterApiCaching().isCached(taskType,ZWOELF_STUNDEN)){
                    loopId++;
                    log.debug(msg+ "### userService.getAllTwitterIds: ("+loopId+")  "+user.getIdTwitter());
                    worklistProfileTwitterIds.add(user.getIdTwitter());
                }
            }
            hasNext = userProfileTwitterIds.hasNext();
            pageRequest = pageRequest.next();
        }
        long number = worklistProfileTwitterIds.size();
        loopId = 0;
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        List<UserMessage> userProfileList = new ArrayList<>();
        for(Long userProfileTwitterId:worklistProfileTwitterIds){
            String counter = " ( " + loopId + " from " + number + " ) ";
            log.debug(msg + counter);
            TwitterProfile userProfile = null;
            loopId++;
            try {
                log.debug(msg+"### twitterApiService.getUserProfileForTwitterId("+userProfileTwitterId+") "+counter);
                userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
            } catch (RateLimitExceededException e) {
                log.error(msg + "### ERROR: twitterApiService.getUserProfileForTwitterId("+userProfileTwitterId+") "+counter,e);
            }
            if(userProfile != null){
                UserMessage userMsg = new UserMessage(msgIn,userProfile);
                userProfileList.add(userMsg);
            }
            log.debug(msg + "### waiting now for (ms): "+millisToWaitBetweenTwoApiCalls);
            try {
                Thread.sleep(millisToWaitBetweenTwoApiCalls);
            } catch (InterruptedException e) {
            }
        }
        log.debug(msg+ " DONE");
        return userProfileList;
    }
}
