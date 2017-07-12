package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface UserTransformService extends TransformService<User,TwitterProfile> {

}
