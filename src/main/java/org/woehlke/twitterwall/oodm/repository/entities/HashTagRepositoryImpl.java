package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindHashTagByTextException;
import org.woehlke.twitterwall.oodm.repository.TweetRepositoryImpl;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class HashTagRepositoryImpl extends DomainRepositoryImpl<HashTag> implements HashTagRepository {

    private static final Logger log = LoggerFactory.getLogger(HashTagRepositoryImpl.class);
    
    @Override
    public HashTag findByText(String text) {
        try {
            String name ="HashTag.findByText";
            TypedQuery<HashTag> query = entityManager.createNamedQuery(name, HashTag.class);
            query.setParameter("text", text);
            HashTag result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + text);
            throw e;
        }
    }
    
}
