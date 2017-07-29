package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.repositories.custom.MediaRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
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
        List<Media> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }
}
