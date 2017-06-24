package org.woehlke.twitterwall.process.parts;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

/**
 * Created by tw on 22.06.17.
 */
public interface UserApiService {

    User getEntitiesForUrlDescription(User user);

}
