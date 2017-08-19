package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.service.UserService;
//import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

/**
 * Created by tw on 11.07.17.
 */

@Component
//@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProfileForScreenNameImpl implements StoreUserProfileForScreenName {

    @Override
    public User storeUserProfileForScreenName(String screenName, Task task){
        String msg = "storeUserProfileForScreenName( screenName = "+screenName+") "+task.getUniqueId()+" : ";
        try {
            if (screenName != null && !screenName.isEmpty()) {
                User userPersForMention = this.userService.findByScreenName(screenName);
                if (userPersForMention == null) {
                    try {
                        TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                        User userFromMention = storeUserProfile.storeUserProfile(twitterProfile, task);
                        log.debug(msg + " userFromMention: " + userFromMention.getUniqueId());
                        return userFromMention;
                    } catch (RateLimitExceededException ex) {
                        log.warn(msg + "" + task.getUniqueId(), ex);
                    }
                }
                return userPersForMention;
            } else {
                log.warn(msg + " " + task.getUniqueId());
                return null;
            }
        } catch (Exception e){
            log.error(msg+e.getMessage());
            return null;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileImpl.class);

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final StoreUserProfile storeUserProfile;

    //private final TaskService taskService;

    @Autowired
    public StoreUserProfileForScreenNameImpl(UserService userService, TwitterApiService twitterApiService, StoreUserProfile storeUserProfile/*, TaskService taskService*/) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.storeUserProfile = storeUserProfile;
        //this.taskService = taskService;
    }

}
