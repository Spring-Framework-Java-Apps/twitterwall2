package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.HashTag;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
        HashTag result = query.getSingleResult();
        return result;
    }
}
