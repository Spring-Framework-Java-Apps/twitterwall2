package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.custom.MentionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
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
        List<Mention> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
