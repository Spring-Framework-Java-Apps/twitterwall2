package org.woehlke.twitterwall.oodm.service.entities;


import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

/**
 * Created by tw on 23.06.17.
 */
public interface UrlCacheService extends DomainService<UrlCache> {

    UrlCache store(UrlCache urlCache);

    UrlCache findByUrl(String url);
}
