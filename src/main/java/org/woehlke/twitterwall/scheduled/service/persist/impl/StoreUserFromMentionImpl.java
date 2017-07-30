package org.woehlke.twitterwall.scheduled.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserFromMention;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

/**
 * Created by tw on 11.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class StoreUserFromMentionImpl implements StoreUserFromMention {

    @Override
    public User storeUserFromMention(User user, Task task) {
        String msg = "storeUserFromMention: "+user.getUniqueId()+" : "+task.getUniqueId()+" : ";
        try {
            for (Mention mention : user.getEntities().getMentions()) {
                String screenName = mention.getScreenName().getScreenName();
                User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(screenName, task);
                log.debug(msg + " userFromScreenName: " + userFromMention.toString());
            }
        } catch (Exception e){
            log.error(msg + e.getMessage());
        }
        return user;
    }

    private static final Logger log = LoggerFactory.getLogger(StoreUserFromMentionImpl.class);

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public StoreUserFromMentionImpl(StoreUserProfileForScreenName storeUserProfileForScreenName1) {
        this.storeUserProfileForScreenName = storeUserProfileForScreenName1;
    }

}
