package org.woehlke.twitterwall.process.backend.service.entities;

import org.springframework.social.twitter.api.MediaEntity;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.process.backend.common.TransformService;

/**
 * Created by tw on 28.06.17.
 */
public interface MediaTransformService extends TransformService<Media,MediaEntity> {


}
