package org.woehlke.twitterwall.scheduled.service.transform.entities;

import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 11.07.17.
 */
public interface EntitiesTransformService {

    Entities getEntitiesForUser(User user);

    Entities transform(org.springframework.social.twitter.api.Entities entities);
}
