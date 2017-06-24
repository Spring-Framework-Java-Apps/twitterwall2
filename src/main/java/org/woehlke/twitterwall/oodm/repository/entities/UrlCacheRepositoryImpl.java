package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.common.UrlCache;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlCacheByUrlException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 23.06.17.
 */
@Repository
public class UrlCacheRepositoryImpl implements UrlCacheRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UrlCache persist(UrlCache urlCache) {
        entityManager.persist(urlCache);
        return urlCache;
    }

    @Override
    public UrlCache findByUrl(String url) {
        try {
            String SQL = "select t from UrlCache as t where t.url=:url";
            TypedQuery<UrlCache> query = entityManager.createQuery(SQL, UrlCache.class);
            query.setParameter("url", url);
            UrlCache result = query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            throw new FindUrlCacheByUrlException(e, url);
        }
    }
}
