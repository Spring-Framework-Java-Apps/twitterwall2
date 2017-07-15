package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface HashTagTransformService extends TransformService<HashTag,HashTagEntity> {

    //Set<HashTag> getHashTagsFor(User user);

    Set<HashTag> getHashTagsFor(TwitterProfile userSource);
}
