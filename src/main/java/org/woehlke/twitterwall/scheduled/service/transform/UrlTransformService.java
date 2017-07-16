package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UrlEntity;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.common.TransformService;

import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
public interface UrlTransformService extends TransformService<Url,UrlEntity> {

    //Set<Url> getUrlsFor(User user);

    Set<Url> getUrlsFor(TwitterProfile userSource);

}
