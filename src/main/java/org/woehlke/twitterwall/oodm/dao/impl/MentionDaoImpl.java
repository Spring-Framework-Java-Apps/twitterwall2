package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.dao.MentionDao;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MentionDaoImpl extends DomainDaoWithIdTwitterImpl<Mention> implements MentionDao {

    private static final Logger log = LoggerFactory.getLogger(MentionDaoImpl.class);

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

    @Override
    public long findLowestIdTwitter(Mention mention) {
        String qname="Mention.findLowestIdTwitter";
        log.debug(qname);
        try {
            TypedQuery<Long> query = entityManager.createNamedQuery(qname, Long.class);
            query.setMaxResults(1);
            List<Long>result = query.getResultList();
            if(result.size()==0){
                log.debug(qname+" not found: return 0");
                return 0;
            } else {
                long lowestIdTwitter = result.iterator().next();
                log.debug(qname+" found: " + lowestIdTwitter + " for "+mention.toString());
                return lowestIdTwitter;
            }
        } catch (EmptyResultDataAccessException e) {
            log.debug(qname+" not found: return 0");
            return 0;
        }
    }
}
