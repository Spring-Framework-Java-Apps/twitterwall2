package org.woehlke.twitterwall.backend.service.persist;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.Task;

/**
 * Created by tw on 09.07.17.
 */
public interface StoreUserProfile {

    User storeUserProfile(TwitterProfile userProfile, Task task);

}
