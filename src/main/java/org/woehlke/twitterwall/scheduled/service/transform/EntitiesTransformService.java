package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Entities;

/**
 * Created by tw on 11.07.17.
 */
public interface EntitiesTransformService {

    Entities transformEntitiesForUser(TwitterProfile userSource);

    Entities transform(org.springframework.social.twitter.api.Entities entities);
}
