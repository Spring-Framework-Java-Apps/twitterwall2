package org.woehlke.twitterwall.scheduled.service.transform.entities;

import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface MentionTransformService extends TransformService<Mention, MentionEntity> {

    //Set<Mention> findByUser(User user);

    Set<Mention> findByUser(TwitterProfile userSource);
}
