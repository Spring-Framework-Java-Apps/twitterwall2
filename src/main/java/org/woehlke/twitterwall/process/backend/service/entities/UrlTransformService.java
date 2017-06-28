package org.woehlke.twitterwall.process.backend.service.entities;

import org.springframework.social.twitter.api.UrlEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.process.backend.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface UrlTransformService extends TransformService<Url,UrlEntity> {

    Set<Url> getUrlsFor(User user);
    
}
