package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface UrlCacheRepositoryCustom extends DomainObjectMinimalRepository<UrlCache> {

    UrlCache findByUniqueId(UrlCache domainObject);
}
