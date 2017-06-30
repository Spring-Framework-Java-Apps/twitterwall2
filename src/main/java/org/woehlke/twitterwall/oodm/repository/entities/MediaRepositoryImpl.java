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
    public Media findByFields(String mediaHttp, String mediaHttps, String url, String display, String expanded, String mediaType) {
        try {
            String name = "Media.findByFields";
            TypedQuery<Media> query = entityManager.createNamedQuery(name, Media.class);
            query.setParameter("mediaHttp", mediaHttp);
            query.setParameter("mediaHttps", mediaHttps);
            query.setParameter("url", url);
            query.setParameter("display", display);
            query.setParameter("expanded", expanded);
            query.setParameter("mediaType", mediaType);
            Media result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.info("not found: mediaHttp", mediaHttp);
            log.info("not found: mediaHttps", mediaHttps);
            log.info("not found: url", url);
            log.info("not found: display", display);
            log.info("not found: expanded", expanded);
            log.info("not found: mediaType", mediaType);
            throw e;
        }
    }



    @Override
    public Media findByUrl(String url) {
        try {
            String name = "Media.findByUrl";
            TypedQuery<Media> query = entityManager.createNamedQuery(name, Media.class);
            query.setParameter("url", url);
            Media result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.info("not found: " + url);
            throw e;
        }
    }
}
