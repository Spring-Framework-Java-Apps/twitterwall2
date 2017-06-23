package org.woehlke.twitterwall.process;

import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 22.06.17.
 */
public interface UserHelperService {

    User getEntitiesForUrlDescription(User user);
}
