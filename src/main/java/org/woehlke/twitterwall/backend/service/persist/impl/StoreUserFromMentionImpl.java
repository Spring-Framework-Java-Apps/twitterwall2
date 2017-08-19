package org.woehlke.twitterwall.backend.service.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.backend.service.persist.StoreUserFromMention;
import org.woehlke.twitterwall.backend.service.persist.StoreUserProfileForScreenName;

/**
 * Created by tw on 11.07.17.
 */
@Component
public class StoreUserFromMentionImpl implements StoreUserFromMention {

    @Override
    public User storeUserFromMention(User user, Task task) {
        String msg = "storeUserFromMention: "+user.getUniqueId()+" : "+task.getUniqueId()+" : ";
        try {
            for (Mention mention : user.getEntities().getMentions()) {
                String screenName = mention.getScreenName();
                User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(screenName, task);
                log.debug(msg + " userFromScreenName: " + userFromMention.getUniqueId());
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
