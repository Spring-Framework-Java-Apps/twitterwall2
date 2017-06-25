package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlCacheByUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.PersistUrlCacheException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 23.06.17.
 */
@Repository
public class UrlCacheRepositoryImpl implements UrlCacheRepository {

    private static final Logger log = LoggerFactory.getLogger(UrlCacheRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UrlCache persist(UrlCache urlCache) {
        if(urlCache.isUrlAndExpandedTheSame()){
            throw new PersistUrlCacheException(urlCache.toString());
        }
        entityManager.persist(urlCache);
        entityManager.flush();
        log.info("persisted: "+urlCache.toString());
        return urlCache;
    }

    @Override
    public UrlCache findByUrl(String url) {
        try {
            String name = "UrlCache.findByUrl";
            TypedQuery<UrlCache> query = entityManager.createNamedQuery(name, UrlCache.class);
            query.setParameter("url", url);
            UrlCache result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + url);
            throw new FindUrlCacheByUrlException(e, url);
        }
    }
}
