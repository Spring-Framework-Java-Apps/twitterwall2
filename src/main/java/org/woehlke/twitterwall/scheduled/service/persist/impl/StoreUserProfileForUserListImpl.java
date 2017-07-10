package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProcess;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForUserList;
import org.woehlke.twitterwall.scheduled.service.transform.UserTransformService;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserProfileForUserListImpl implements StoreUserProfileForUserList {

    private static final Logger log = LoggerFactory.getLogger(StoreUserProfileForUserListImpl.class);

    private final UserTransformService userTransformService;

    private final StoreUserProfile storeUserProfile;

    private final StoreUserProcess storeUserProcess;

    @Autowired
    public StoreUserProfileForUserListImpl(UserTransformService userTransformService, StoreUserProfile storeUserProfile, StoreUserProcess storeUserProcess) {
        this.userTransformService = userTransformService;
        this.storeUserProfile = storeUserProfile;
        this.storeUserProcess = storeUserProcess;
    }

    @Override
    public User storeUserProfileForUserList(TwitterProfile twitterProfile, Task task) {
        String msg = "storeUserProfileForUserList: idTwitter="+twitterProfile.getId();
        User user = userTransformService.transform(twitterProfile);
        user.setOnDefinedUserList(true);
        user = storeUserProcess.storeUserProcess(user, task);
        for(Mention mention:user.getMentions()){
            String screenName = mention.getScreenName();
            if(screenName != null){
                User userFromMention = storeUserProfile.storeUserProfileForScreenName(screenName, task);
                log.debug(msg+"storeUserProfile.storeUserProfileForScreenName("+screenName+") = "+userFromMention.toString());
            }
        }
        return user;
    }
}
