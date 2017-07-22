package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UrlCacheRepository extends DomainRepository<UrlCache> {

    @Query(name="UrlCache.findByUniqueId")
    UrlCache findByUniqueId(@Param("domainObject") UrlCache domainObject);

    UrlCache findByUrl(String url);
}
