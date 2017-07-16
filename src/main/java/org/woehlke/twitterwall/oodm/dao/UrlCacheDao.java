package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;

/**
 * Created by tw on 23.06.17.
 */
public interface UrlCacheDao extends DomainDao<UrlCache> {

    UrlCache findByUrl(String url);
}
