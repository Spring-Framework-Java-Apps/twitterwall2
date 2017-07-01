package org.woehlke.twitterwall.scheduled.service;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface UserTransformService extends TransformService<User,TwitterProfile> {

    User getEntitiesForUrlDescription(User user);
}
