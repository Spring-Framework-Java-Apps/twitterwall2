package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

/**
 * Created by tw on 09.07.17.
 */

@Component
public class StoreUserProfileImpl implements StoreUserProfile {

    @Override
    public User storeUserProfile(TwitterProfile userProfile, Task task) {
        String msg = "storeUserProfile: "+userProfile.getScreenName() + ": "+task.getUniqueId() + " : ";
        try {
            User user = userTransformService.transform(userProfile, task);
            user = storeUserProcess.storeUserProcess(user, task);
            return user;
        } catch (Exception e){
            log.error(msg+e.getMessage());
            return null;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileImpl.class);

    private final UserTransformService userTransformService;

    private final StoreUserProcess storeUserProcess;

    @Autowired
    public StoreUserProfileImpl(UserTransformService userTransformService, StoreUserProcess storeUserProcess) {
        this.userTransformService = userTransformService;
        this.storeUserProcess = storeUserProcess;
    }

}
