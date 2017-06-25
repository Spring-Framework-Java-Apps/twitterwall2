package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindMentionByScreenNameAndNameException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MentionRepositoryImpl implements MentionRepository {

    private static final Logger log = LoggerFactory.getLogger(MentionRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mention persist(Mention myMentionEntity) {
        entityManager.persist(myMentionEntity);
        entityManager.flush();
        log.info("persisted: "+myMentionEntity.toString());
        return myMentionEntity;
    }

    @Override
    public Mention update(Mention myMentionEntity) {
        myMentionEntity = entityManager.merge(myMentionEntity);
        entityManager.flush();
        log.info("merged: "+myMentionEntity.toString());
        return myMentionEntity;
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        try {
            String name="Mention.findByIdTwitter";
            TypedQuery<Mention> query = entityManager.createNamedQuery(name, Mention.class);
            query.setParameter("idTwitter", idTwitter);
            Mention result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + idTwitter);
            throw new FindMentionByIdTwitterException(e, idTwitter);
        }
    }

    @Override
    public Mention findByScreenNameAndName(String screenName, String name) {
        try {
            String qname="Mention.findByScreenNameAndName";
            TypedQuery<Mention> query = entityManager.createNamedQuery(qname, Mention.class);
            query.setParameter("screenName", screenName);
            query.setParameter("name", name);
            Mention result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + screenName);
            log.info("not found: " + name);
            throw new FindMentionByScreenNameAndNameException(e, screenName, name);
        }
    }
}
