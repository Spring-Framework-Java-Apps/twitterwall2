package org.woehlke.twitterwall.backend.service.transform;

import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.Media;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.backend.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface MediaTransformService extends TransformService<Media,MediaEntity> {

    Set<Media> getMediaFor(TwitterProfile userSource, Task task);

}
