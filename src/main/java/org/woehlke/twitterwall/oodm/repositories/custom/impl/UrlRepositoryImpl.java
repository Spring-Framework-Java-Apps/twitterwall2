package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UrlRepositoryImpl implements UrlRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UrlRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Url findByUniqueId(Url domainObject) {
        String name="Url.findByUniqueId";
        TypedQuery<Url> query = entityManager.createNamedQuery(name,Url.class);
        query.setParameter("url",domainObject.getUrl());
        List<Url> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
