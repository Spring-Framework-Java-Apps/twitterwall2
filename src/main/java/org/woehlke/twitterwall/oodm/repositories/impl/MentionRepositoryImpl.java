package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Mention;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class MentionRepositoryImpl implements MentionRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MentionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Mention findByUniqueId(Mention domainObject) {
        String name="Mention.findByUniqueId";
        TypedQuery<Mention> query = entityManager.createNamedQuery(name,Mention.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        query.setParameter("screenName",domainObject.getScreenName());
        Mention result = query.getSingleResult();
        return result;
    }
}
