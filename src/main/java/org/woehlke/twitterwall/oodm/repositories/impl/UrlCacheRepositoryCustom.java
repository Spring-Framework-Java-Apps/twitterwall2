package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.UrlCache;

public interface UrlCacheRepositoryCustom {

    UrlCache findByUniqueId(UrlCache domainObject);
}
