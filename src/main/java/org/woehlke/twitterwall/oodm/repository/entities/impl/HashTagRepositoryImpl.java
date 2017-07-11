package org.woehlke.twitterwall.oodm.repository.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.common.impl.DomainRepositoryImpl;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class HashTagRepositoryImpl extends DomainRepositoryImpl<HashTag> implements HashTagRepository {

    private static final Logger log = LoggerFactory.getLogger(HashTagRepositoryImpl.class);

    @Override
    public HashTag findByText(String text) {
        String name ="HashTag.findByText";
        log.debug(name);
        try {
            TypedQuery<HashTag> query = entityManager.createNamedQuery(name, HashTag.class);
            query.setParameter("text", text);
            HashTag result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + text);
            throw e;
        }
    }

}
