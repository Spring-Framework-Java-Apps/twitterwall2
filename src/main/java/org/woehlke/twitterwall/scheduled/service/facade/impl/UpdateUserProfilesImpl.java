package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateUserProfilesImpl implements UpdateUserProfiles {

    @Override
    public void updateUserProfiles(){
        String msg = "update User Profiles From ProfileId: ";
        log.debug(msg+"---------------------------------------");
        log.debug(msg + "START - The time is now {}", dateFormat.format(new Date()));
        int allLoop = 0;
        int loopId = 0;
        Task task = this.taskService.create(msg, TaskType.UPDATE_USER_PROFILES);
        try {
            boolean hasNext;
            Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, pageSize);
            do {
                Page<Long> userProfileTwitterIds = userService.getAllTwitterIds(pageRequest);
                hasNext = userProfileTwitterIds.hasNext();
                long number = userProfileTwitterIds.getTotalElements();
                for (Long userProfileTwitterId : userProfileTwitterIds) {
                    allLoop++;
                    loopId++;
                    String counter = " ( " + loopId + " from " + number + " ) [" + allLoop + "] ";
                    try {
                        log.debug(msg + counter);
                        TwitterProfile userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
                        User user = storeUserProfile.storeUserProfile(userProfile, task);
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
                                this.taskService.warn(task, exe, msg + subCounter);
                            }
                        }
                        log.debug(msg + user.toString());
                        log.debug(msg + "-----------------------------------------------------");
                        log.debug(msg + "Start SLEEP for " + millisToWaitBetweenTwoApiCalls + " ms " + counter);
                        Thread.sleep(millisToWaitBetweenTwoApiCalls);
                        log.debug(msg + "Done SLEEP for " + millisToWaitBetweenTwoApiCalls + " ms " + counter);
                        log.debug(msg + "-----------------------------------------------------");
                    } catch (RateLimitExceededException e) {
                        this.taskService.warn(task, e, msg);
                    } catch (InterruptedException ex) {
                        this.taskService.warn(task, ex, msg);
                    } finally {
                        log.debug(msg + "---------------------------------------");
                    }
                }
                pageRequest = pageRequest.next();
            } while (hasNext);
        } catch (ResourceAccessException e) {
            this.taskService.warn(task,e,msg);
        } catch (RateLimitExceededException e) {
            this.taskService.warn(task,e,msg);
        }
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        task.event(report);
        this.taskService.done(task);
        log.debug(msg +"---------------------------------------");
        log.debug(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitBetweenTwoApiCalls}")
    private int millisToWaitBetweenTwoApiCalls;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public UpdateUserProfilesImpl(StoreUserProfile storeUserProfile, TwitterApiService twitterApiService, UserService userService, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName) {
        this.storeUserProfile = storeUserProfile;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
    }
}
