package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.repositories.custom.MediaRepositoryCustom;

import javax.persistence.EntityManager;

@Repository
public class MediaRepositoryImpl extends AbstractDomainRepositoryImpl<Media> implements MediaRepositoryCustom {


    @Autowired
    public MediaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
