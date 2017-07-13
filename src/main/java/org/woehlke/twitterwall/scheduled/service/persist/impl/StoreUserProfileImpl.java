package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.transform.EntitiesTransformService;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProfileImpl implements StoreUserProfile {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileImpl.class);

    private final UserTransformService userTransformService;

    private final StoreUserProcess storeUserProcess;



    @Autowired
    public StoreUserProfileImpl(UserTransformService userTransformService, StoreUserProcess storeUserProcess) {
        this.userTransformService = userTransformService;
        this.storeUserProcess = storeUserProcess;
    }

    @Override
    public User storeUserProfile(TwitterProfile userProfile, Task task) {
        String msg = "storeUserProfile: ";
        User user = userTransformService.transform(userProfile);
        user = storeUserProcess.storeUserProcess(user, task);
        return user;
    }

}
