package org.woehlke.twitterwall.process.backend.service;

import org.woehlke.twitterwall.process.backend.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface TweetTransformService extends TransformService<org.woehlke.twitterwall.oodm.entities.Tweet, org.springframework.social.twitter.api.Tweet> {
}
