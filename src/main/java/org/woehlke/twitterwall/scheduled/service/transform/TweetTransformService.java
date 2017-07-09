package org.woehlke.twitterwall.scheduled.service.transform;

import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface TweetTransformService extends TransformService<org.woehlke.twitterwall.oodm.entities.Tweet, org.springframework.social.twitter.api.Tweet> {
}
