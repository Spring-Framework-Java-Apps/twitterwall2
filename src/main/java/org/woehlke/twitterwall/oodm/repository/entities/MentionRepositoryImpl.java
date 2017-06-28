package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByIdTwitterAndScreenNameException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByScreenNameAndNameException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByScreenNameException;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MentionRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<Mention> implements MentionRepository {

    private static final Logger log = LoggerFactory.getLogger(MentionRepositoryImpl.class);
    
    @Override
    public Mention findByScreenName(String screenName) {
        try {
            String qname="Mention.findByScreenName";
            TypedQuery<Mention> query = entityManager.createNamedQuery(qname, Mention.class);
            query.setParameter("screenName", screenName);
            Mention result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + screenName);
            throw new FindMentionByScreenNameException(e, screenName);
        }
    }

    @Override
    public Mention findByIdTwitterAndScreenName(Long idTwitter, String screenName) {
        try {
            String qname="Mention.findByIdTwitterAndScreenName";
            TypedQuery<Mention> query = entityManager.createNamedQuery(qname, Mention.class);
            query.setParameter("idTwitter", idTwitter);
            query.setParameter("screenName", screenName);
            Mention result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + screenName);
            throw e;
        }
    }
}
