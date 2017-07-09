package org.woehlke.twitterwall.scheduled.service.persist;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 09.07.17.
 */
public interface StoreUserProfile {

    User storeUserProfile(TwitterProfile userProfile);

    User storeUserProfileForScreenName(String screenName);

    User findByScreenName(String screenName);
}
