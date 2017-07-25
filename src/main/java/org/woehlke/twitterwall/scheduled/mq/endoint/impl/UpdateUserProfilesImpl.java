package org.woehlke.twitterwall.scheduled.mq.endoint.impl;

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
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endoint.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

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
    public List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message) {
        String msg = "mqUpdateUserProfiles.splitMessage: ";
        log.debug(msg+ " START");
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        int allLoop = 0;
        int loopId = 0;
        boolean hasNext=true;
        List<Long> worklistProfileTwitterIds = new ArrayList<>();
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<User> userProfileTwitterIds = userService.getAll(pageRequest);
            for(User user:userProfileTwitterIds.getContent()){
                log.debug(msg+ " userService.getAllTwitterIds:  "+user.getIdTwitter());
                worklistProfileTwitterIds.add(user.getIdTwitter());
            }
            hasNext = userProfileTwitterIds.hasNext();
            pageRequest = pageRequest.next();
        }
        long number = worklistProfileTwitterIds.size();
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        List<TwitterProfileMessage> userProfileList = new ArrayList<>();
        for(Long userProfileTwitterId:worklistProfileTwitterIds){
            allLoop++;
            loopId++;
            String counter = " ( " + loopId + " from " + number + " ) [" + allLoop + "] ";
            log.debug(msg + counter);
            TwitterProfile userProfile = null;
            try {
                userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
            } catch (RateLimitExceededException e) {
                log.error(msg + counter+ "twitterApiService.getUserProfileForTwitterId("+userProfileTwitterId+") ",e);
            }
            if(userProfile != null){
                TwitterProfileMessage userMsg = new TwitterProfileMessage(msgIn,userProfile);
                userProfileList.add(userMsg);
            }
            try {
                Thread.sleep(millisToWaitBetweenTwoApiCalls);
            } catch (InterruptedException e) {
            }
        }
        log.debug(msg+ " DONE");
        return userProfileList;
    }
}
