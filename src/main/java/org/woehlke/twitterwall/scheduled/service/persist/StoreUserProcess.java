package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 09.07.17.
 */
public interface StoreUserProcess {

    User storeUserProcess(User user);
}
