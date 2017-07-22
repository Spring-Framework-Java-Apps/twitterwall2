package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlCacheRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UrlCacheRepository extends DomainRepository<UrlCache>,UrlCacheRepositoryCustom {

    UrlCache findByUrl(String url);
}
