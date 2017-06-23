package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;

/**
 * Created by tw on 23.06.17.
 */
public interface UrlCacheRepository {

    UrlCache persist(UrlCache urlCache);

    UrlCache findByUrl(String url);
}
