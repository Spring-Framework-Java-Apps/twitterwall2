package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.dao.UrlCacheDao;
import org.woehlke.twitterwall.oodm.entities.UrlCache;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 23.06.17.
 */
@Repository
public class UrlCacheDaoImpl extends DomainDaoImpl<UrlCache> implements UrlCacheDao {

    private static final Logger log = LoggerFactory.getLogger(UrlCacheDaoImpl.class);

    @Override
    public UrlCache findByUrl(String url) {
        String name = "UrlCache.findByUrl";
        log.debug(name);
        try {
            TypedQuery<UrlCache> query = entityManager.createNamedQuery(name, UrlCache.class);
            query.setParameter("url", url);
            UrlCache result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + url);
            throw e;
        }
    }
}
