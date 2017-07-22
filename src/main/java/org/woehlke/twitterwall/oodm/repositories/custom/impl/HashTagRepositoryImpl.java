package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.custom.HashTagRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class HashTagRepositoryImpl implements HashTagRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public HashTagRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public HashTag findByUniqueId(HashTag domainObject) {
        String name="HashTag.findByUniqueId";
        TypedQuery<HashTag> query = entityManager.createNamedQuery(name,HashTag.class);
        query.setParameter("text",domainObject.getText());
        List<HashTag> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
