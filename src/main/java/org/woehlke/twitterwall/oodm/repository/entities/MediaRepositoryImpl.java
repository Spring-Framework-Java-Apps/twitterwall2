package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MediaRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<Media> implements MediaRepository {

    private static final Logger log = LoggerFactory.getLogger(MediaRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Media findByUrl(String url) {
        String name = "Media.findByUrl";
        log.debug(name);
        try {
            TypedQuery<Media> query = entityManager.createNamedQuery(name, Media.class);
            query.setParameter("url", url);
            Media result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + url);
            throw e;
        }
    }
}
