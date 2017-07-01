package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 23.06.17.
 */
@Repository
public class UrlCacheRepositoryImpl extends DomainRepositoryImpl<UrlCache> implements UrlCacheRepository {

    private static final Logger log = LoggerFactory.getLogger(UrlCacheRepositoryImpl.class);

    @Override
    public UrlCache findByUrl(String url) {
        String name = "UrlCache.findByUrl";
        try {
            TypedQuery<UrlCache> query = entityManager.createNamedQuery(name, UrlCache.class);
            query.setParameter("url", url);
            UrlCache result = query.getSingleResult();
            log.info(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.info(name+" not found: " + url);
            throw e;
        }
    }
}
