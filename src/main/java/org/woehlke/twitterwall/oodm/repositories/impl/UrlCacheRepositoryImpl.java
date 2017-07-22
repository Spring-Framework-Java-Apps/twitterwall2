package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.UrlCache;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class UrlCacheRepositoryImpl implements UrlCacheRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UrlCacheRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UrlCache findByUniqueId(UrlCache domainObject) {
        String name="UrlCache.findByUniqueId";
        TypedQuery<UrlCache> query = entityManager.createNamedQuery(name,UrlCache.class);
        query.setParameter("url",domainObject.getUrl());
        UrlCache result = query.getSingleResult();
        return result;
    }
}
