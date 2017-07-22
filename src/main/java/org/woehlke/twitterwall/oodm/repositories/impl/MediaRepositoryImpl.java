package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Media;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class MediaRepositoryImpl implements MediaRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MediaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Media findByUniqueId(Media domainObject) {
        String name="Media.findByUniqueId";
        TypedQuery<Media> query = entityManager.createNamedQuery(name,Media.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        Media result = query.getSingleResult();
        return result;
    }
}
