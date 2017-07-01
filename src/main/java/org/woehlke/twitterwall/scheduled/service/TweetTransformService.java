package org.woehlke.twitterwall.scheduled.service;

import org.woehlke.twitterwall.scheduled.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface TweetTransformService extends TransformService<org.woehlke.twitterwall.oodm.entities.Tweet, org.springframework.social.twitter.api.Tweet> {
}
