package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.dao.parts.impl.DomainDaoImpl;
import org.woehlke.twitterwall.oodm.dao.HashTagDao;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class HashTagDaoImpl extends DomainDaoImpl<HashTag> implements HashTagDao {

    private static final Logger log = LoggerFactory.getLogger(HashTagDaoImpl.class);

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
