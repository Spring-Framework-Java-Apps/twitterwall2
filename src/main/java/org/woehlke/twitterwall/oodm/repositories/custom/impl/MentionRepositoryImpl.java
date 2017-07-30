package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.repositories.custom.MentionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MentionRepositoryImpl extends AbstractDomainRepositoryImpl<Mention> implements MentionRepositoryCustom {


    @Autowired
    public MentionRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
