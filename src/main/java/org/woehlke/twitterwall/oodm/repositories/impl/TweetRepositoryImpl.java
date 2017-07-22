package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class TweetRepositoryImpl implements TweetRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public TweetRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tweet findByUniqueId(Tweet domainObject) {
        String name="Tweet.findByUniqueId";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name,Tweet.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        Tweet result = query.getSingleResult();
        return result;
    }
}
