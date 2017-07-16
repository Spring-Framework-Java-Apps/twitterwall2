package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.dao.MediaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MediaDaoImpl extends DomainDaoWithIdTwitterImpl<Media> implements MediaDao {

    private static final Logger log = LoggerFactory.getLogger(MediaDaoImpl.class);

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
