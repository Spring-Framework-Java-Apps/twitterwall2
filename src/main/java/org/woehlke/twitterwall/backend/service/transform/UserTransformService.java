package org.woehlke.twitterwall.backend.service.transform;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.backend.service.transform.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface UserTransformService extends TransformService<User,TwitterProfile> {

}
