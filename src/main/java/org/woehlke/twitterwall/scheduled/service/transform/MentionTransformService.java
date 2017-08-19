package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface MentionTransformService extends TransformService<Mention, MentionEntity> {

    Set<Mention> findByUser(TwitterProfile userSource, Task task);

    Set<Mention> findByTweet(Tweet tweetSource, Task task);
}
