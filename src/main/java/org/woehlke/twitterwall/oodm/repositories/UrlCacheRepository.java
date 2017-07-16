package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.UrlCache;

/**
 * Created by tw on 15.07.17.
 */
public interface UrlCacheRepository extends PagingAndSortingRepository<UrlCache,Long> {

    UrlCache findByUrl(String url);
}
