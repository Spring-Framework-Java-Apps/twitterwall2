package org.woehlke.twitterwall.scheduled.mq.endpoint.users.impl;

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
import org.woehlke.twitterwall.scheduled.mq.endpoint.users.UpdateUsersSplitter;
import org.woehlke.twitterwall.scheduled.mq.endpoint.common.TwitterwallMessageBuilder;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.UserMessage;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.ScheduledTasks.TWELVE_HOURS;
import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateUserSplitter")
public class UpdateUsersSplitterImpl implements UpdateUsersSplitter {

    private static final Logger log = LoggerFactory.getLogger(UpdateUsersSplitterImpl.class);

    private final TwitterProperties twitterProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final UserService userService;

    private final CountedEntitiesService countedEntitiesService;

    private final TwitterwallMessageBuilder twitterwallMessageBuilder;

    @Autowired
    public UpdateUsersSplitterImpl(TwitterProperties twitterProperties, TwitterApiService twitterApiService, TaskService taskService, UserService userService, CountedEntitiesService countedEntitiesService, TwitterwallMessageBuilder twitterwallMessageBuilder) {
        this.twitterProperties = twitterProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.userService = userService;
        this.countedEntitiesService = countedEntitiesService;
        this.twitterwallMessageBuilder = twitterwallMessageBuilder;
    }

    @Override
    public List<Message<UserMessage>> splitUserMessage(Message<TaskMessage> incomingTaskMessage) {
        String msg = "### mqUpdateUserProfiles.splitTweetMessage: ";
        log.debug(msg+ " START");
        CountedEntities countedEntities = countedEntitiesService.countAll();
        TaskMessage msgIn = incomingTaskMessage.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        TaskType taskType = task.getTaskType();
        int loopId = 0;
        int loopAll = 0;
        boolean hasNext=true;
        List<Long> worklistProfileTwitterIds = new ArrayList<>();
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<User> userProfileTwitterIds = userService.getAll(pageRequest);
            for(User user:userProfileTwitterIds.getContent()){
                if(!user.getTaskBasedCaching().isCached(taskType, TWELVE_HOURS)){
                    loopId++;
                    loopAll++;
                    log.debug(msg+ "### userService.getAllTwitterIds: ("+loopId+")  "+user.getIdTwitter());
                    worklistProfileTwitterIds.add(user.getIdTwitter());
                }
            }
            hasNext = userProfileTwitterIds.hasNext();
            pageRequest = pageRequest.next();
        }
        long number = worklistProfileTwitterIds.size();
        loopId = 0;
        List<Message<UserMessage>> userProfileList = new ArrayList<>();
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
                Message<UserMessage> mqMessageOut = twitterwallMessageBuilder.buildUserMessage(incomingTaskMessage,userProfile,loopId,loopAll);
                userProfileList.add(mqMessageOut);
            }
            twitterwallMessageBuilder.waitForApi();
        }
        log.debug(msg+ " DONE");
        return userProfileList;
    }
}
