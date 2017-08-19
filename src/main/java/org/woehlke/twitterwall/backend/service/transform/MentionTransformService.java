package org.woehlke.twitterwall.backend.service.transform;

import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.backend.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface MentionTransformService extends TransformService<Mention, MentionEntity> {

    Set<Mention> findByUser(TwitterProfile userSource, Task task);

    Set<Mention> findByTweet(Tweet tweetSource, Task task);
}
