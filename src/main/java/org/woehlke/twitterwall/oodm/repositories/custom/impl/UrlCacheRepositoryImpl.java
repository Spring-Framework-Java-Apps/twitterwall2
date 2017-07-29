package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlCacheRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        List<UrlCache> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
