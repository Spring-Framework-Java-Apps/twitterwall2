package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.Task;

/**
 * Created by tw on 11.07.17.
 */
public interface StoreUserProfileForScreenName {

    User storeUserProfileForScreenName(String screenName, Task task);
}
