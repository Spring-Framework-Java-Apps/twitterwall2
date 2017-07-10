package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateUserProfilesImpl implements UpdateUserProfiles {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final UserService userService;

    private final TaskService taskService;

    @Autowired
    public UpdateUserProfilesImpl(StoreUserProfile storeUserProfile, TwitterApiService twitterApiService, UserService userService, TaskService taskService) {
        this.storeUserProfile = storeUserProfile;
        this.twitterApiService = twitterApiService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public void updateUserProfiles(){
        String msg = "update User Profiles From ProfileId: ";
        log.debug(msg+"---------------------------------------");
        log.debug(msg + "START - The time is now {}", dateFormat.format(new Date()));
        Task task = this.taskService.create(msg, TaskType.UPDATE_USER_PROFILES);
        try {
            List<Long> userProfileTwitterIds = userService.getAllTwitterIds();
            for (Long userProfileTwitterId : userProfileTwitterIds) {
                try {
                    TwitterProfile userProfile = twitterApiService.getUserProfileForTwitterId(userProfileTwitterId);
                    User user = storeUserProfile.storeUserProfile(userProfile,task);
                    log.debug(msg + user.toString());
                } catch (RateLimitExceededException e) {
                    log.warn(msg + e.getMessage());
                    Throwable t = e.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                }  finally {
                    log.debug(msg +"---------------------------------------");
                }
            }
        } catch (ResourceAccessException e) {
            this.taskService.error(task,e);
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            log.error(msg + " check your Network Connection!");
            //throw e;
        } catch (RateLimitExceededException e) {
            this.taskService.error(task,e);
            log.warn(msg + e.getMessage());
            //throw e;
        }
        this.taskService.done(task);
        log.debug(msg +"---------------------------------------");
        log.debug(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }
}
