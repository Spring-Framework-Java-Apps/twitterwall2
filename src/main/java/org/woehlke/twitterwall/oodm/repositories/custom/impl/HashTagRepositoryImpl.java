package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.custom.HashTagRepositoryCustom;

import javax.persistence.EntityManager;

@Repository
public class HashTagRepositoryImpl extends AbstractDomainRepositoryImpl<HashTag> implements HashTagRepositoryCustom {

    @Autowired
    public HashTagRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
