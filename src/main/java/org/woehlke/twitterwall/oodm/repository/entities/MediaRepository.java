package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitter;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface MediaRepository extends DomainRepositoryWithIdTwitter<Media>,DomainRepositoryWithUrl<Media> {
    
    Media findByFields(String mediaHttp, String mediaHttps, String url, String display, String expanded, String type);
}
