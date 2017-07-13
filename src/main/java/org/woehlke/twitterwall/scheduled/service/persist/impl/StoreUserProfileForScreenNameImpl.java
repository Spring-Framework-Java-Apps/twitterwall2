package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProfileForScreenNameImpl implements StoreUserProfileForScreenName {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileImpl.class);

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final StoreUserProfile storeUserProfile;

    private final TaskService taskService;

    @Autowired
    public StoreUserProfileForScreenNameImpl(UserService userService, TwitterApiService twitterApiService, StoreUserProfile storeUserProfile, TaskService taskService) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.storeUserProfile = storeUserProfile;
        this.taskService = taskService;
    }

    @Override
    public User storeUserProfileForScreenName(String screenName, Task task){
        String msg = "storeUserProfileForScreenName( screenName = "+screenName+") ";
        if(screenName != null && !screenName.isEmpty()) {
            try {
                User userPersForMention = this.userService.findByScreenName(screenName);
                return userPersForMention;
            } catch (EmptyResultDataAccessException e) {
                try {
                    TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                    User userFromMention = storeUserProfile.storeUserProfile(twitterProfile,task);
                    log.debug(msg + " userFromMention: "+userFromMention.toString());
                    return userFromMention;
                } catch (RateLimitExceededException ex) {
                    taskService.warn(task,ex,msg);
                    throw e;
                }
            }
        } else {
            taskService.event(task,msg+" throw new IllegalArgumentException");
            throw new IllegalArgumentException("screenName is empty");
        }
    }
}
