package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.UrlEntity;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface UrlService extends DomainService<Url> {

    Url findByUrl(String url);

    Url getPersistentUrlFor(String url);

    List<Url> getTestData();
    
}
