package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.MediaEntity;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaService extends DomainService<Media> {
    
    Media findByIdTwitter(long idTwitter);
}
