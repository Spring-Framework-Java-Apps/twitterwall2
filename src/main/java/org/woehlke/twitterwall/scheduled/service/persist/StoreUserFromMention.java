package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;

/**
 * Created by tw on 11.07.17.
 */
public interface StoreUserFromMention {

    User storeUserFromMention(User user, Task task);
}
