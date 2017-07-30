package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.repositories.custom.UrlRepositoryCustom;

import javax.persistence.EntityManager;

@Repository
public class UrlRepositoryImpl extends AbstractDomainRepositoryImpl<Url> implements UrlRepositoryCustom {

    @Autowired
    public UrlRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
