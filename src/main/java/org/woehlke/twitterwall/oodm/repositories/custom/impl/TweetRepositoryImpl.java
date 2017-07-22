package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.repositories.custom.TweetRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        List<Tweet> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
