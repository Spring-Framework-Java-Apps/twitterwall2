package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MentionRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<Mention> implements MentionRepository {

    private static final Logger log = LoggerFactory.getLogger(MentionRepositoryImpl.class);

    @Override
    public Mention findByScreenName(String screenName) {
        String qname="Mention.findByScreenName";
        log.debug(qname);
        try {
            TypedQuery<Mention> query = entityManager.createNamedQuery(qname, Mention.class);
            query.setParameter("screenName", screenName);
            Mention result = query.getSingleResult();
            log.debug(qname+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(qname+" not found: " + screenName);
            throw e;
        }
    }

    @Override
    public Mention findByIdTwitterAndScreenName(Long idTwitter, String screenName) {
        String qname="Mention.findByIdTwitterAndScreenName";
        log.debug(qname);
        try {
            TypedQuery<Mention> query = entityManager.createNamedQuery(qname, Mention.class);
            query.setParameter("idTwitter", idTwitter);
            query.setParameter("screenName", screenName);
            Mention result = query.getSingleResult();
            log.debug(qname+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(qname+" not found: " + screenName);
            throw e;
        }
    }
}
