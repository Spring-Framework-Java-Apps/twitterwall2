package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.conf.TwitterwallBackendProperties;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateUserProfilesImpl implements UpdateUserProfiles {

    @Override
    public void updateUserProfiles(){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        String msg = "update User Profiles From ProfileId: ";
        log.debug(msg+"---------------------------------------");
        log.debug(msg + "START - The time is now {}", dateFormat.format(new Date()));
        int allLoop = 0;
        int loopId = 0;
        Task task = this.taskService.create(msg, TaskType.UPDATE_USER_PROFILES,countedEntities);
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<Long> userProfileTwitterIds = userService.getAllTwitterIds(pageRequest);
            hasNext = userProfileTwitterIds.hasNext();
            long number = userProfileTwitterIds.getTotalElements();
            for (Long userProfileTwitterId : userProfileTwitterIds) {
                allLoop++;
                loopId++;
                String counter = " ( " + loopId + " from " + number + " ) [" + allLoop + "] ";
                log.debug(msg + counter);
                TwitterProfile userProfile = null;
                try {
                    userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
                } catch (RateLimitExceededException e) {
                    this.taskService.warn(task, e, msg,countedEntities);
                }
                User user = storeUserProfile.storeUserProfile(userProfile, task);
                if(user!=null){
                    log.debug(msg + counter + user.toString());
                    int subLoopId = 0;
                    int subNumber = user.getEntities().getMentions().size();
                    for (Mention mention : user.getEntities().getMentions()) {
                        allLoop++;
                        subLoopId++;
                        String subCounter = counter + " ( " + subLoopId + " from " + subNumber + " ) [" + allLoop + "] ";
                        try {
                            log.debug(msg + subCounter);
                            User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(), task);
                            log.debug(msg + subCounter + userFromMention.toString());
                        } catch (IllegalArgumentException exe) {
                            this.taskService.warn(task, exe, msg + subCounter,countedEntities);
                        }
                    }
                    log.debug(msg + user.toString());
                }
                log.debug(msg + "-----------------------------------------------------");
                log.debug(msg + "Start SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                try {
                    Thread.sleep(twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls());
                } catch (InterruptedException ex) {
                    log.debug(msg+" "+task.toString(),ex);
                }
                log.debug(msg + "Done SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                log.debug(msg + "-----------------------------------------------------");

                log.debug(msg + "---------------------------------------");
            }
            pageRequest = pageRequest.next();
        }
        countedEntities = countedEntitiesService.countAll();
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        taskService.event(task,report,countedEntities);
        this.taskService.done(task,countedEntities);
        log.debug(msg +"---------------------------------------");
        log.debug(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final TwitterwallBackendProperties twitterwallBackendProperties;

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterProperties twitterProperties;

    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public UpdateUserProfilesImpl(TwitterwallBackendProperties twitterwallBackendProperties, TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterProperties twitterProperties, StoreUserProfile storeUserProfile, TwitterApiService twitterApiService, UserService userService, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName, CountedEntitiesService countedEntitiesService) {
        this.twitterwallBackendProperties = twitterwallBackendProperties;
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterProperties = twitterProperties;
        this.storeUserProfile = storeUserProfile;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
        this.countedEntitiesService = countedEntitiesService;
    }
}
