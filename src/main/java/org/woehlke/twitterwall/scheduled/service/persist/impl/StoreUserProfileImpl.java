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
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProfileImpl implements StoreUserProfile {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileImpl.class);

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final UserTransformService userTransformService;

    private final StoreUserProcess storeUserProcess;

    @Autowired
    public StoreUserProfileImpl(UserService userService, TwitterApiService twitterApiService, UserTransformService userTransformService, StoreUserProcess storeUserProcess) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.userTransformService = userTransformService;
        this.storeUserProcess = storeUserProcess;
    }

    @Override
    public User storeUserProfile(TwitterProfile userProfile, Task task) {
        String msg = "storeUserProfile: ";
        User user = userTransformService.transform(userProfile);
        user.setOnDefinedUserList(false);
        user = storeUserProcess.storeUserProcess(user, task);
        for(Mention mention:user.getMentions()){
            String screenName = mention.getScreenName();
            User userFromMention = this.storeUserProfileForScreenName(screenName, task);
            log.debug(msg + " userFromScreenName: "+userFromMention.toString());
        }
        return user;
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
                    User userFromMention = this.storeUserProfile(twitterProfile,task);
                    log.debug(msg + " userFromMention: "+userFromMention.toString());
                    return userFromMention;
                } catch (RateLimitExceededException ex) {
                    log.warn(msg + ex.getMessage());
                    Throwable t = ex.getCause();
                    while(t != null){
                        log.warn(msg + t.getMessage());
                        t = t.getCause();
                    }
                    throw e;
                }
            }
        } else  {
            throw new IllegalArgumentException("screenName is empty");
        }
    }

    /*
    @Override
    public User findByScreenName(String screenName) {
        return this.userService.findByScreenName(screenName);
    }
*/
}
