package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

/**
 * Created by tw on 23.06.17.
 */
public interface UrlCacheRepository extends OodmRepository {

    UrlCache persist(UrlCache urlCache);

    UrlCache findByUrl(String url);
}
