package org.woehlke.twitterwall.scheduled.service.persist;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;

/**
 * Created by tw on 09.07.17.
 */
public interface StoreUserProfile {

    User storeUserProfile(TwitterProfile userProfile, Task task);

    User storeUserProfileForScreenName(String screenName, Task task);

    User findByScreenName(String screenName);
}
