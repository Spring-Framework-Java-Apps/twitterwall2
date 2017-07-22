package org.woehlke.twitterwall.scheduled.service.facade;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Mention;

/**
 * Created by tw on 14.07.17.
 */
public interface StoreTwitterProfileForProxyMentionForUser {

    User storeTwitterProfileForProxyMentionForUser(Mention mention, Task task);
}
