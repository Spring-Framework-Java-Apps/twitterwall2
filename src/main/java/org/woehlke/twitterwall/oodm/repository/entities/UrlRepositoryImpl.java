package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByDisplayExpandedUrlException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUrlByUrlException;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class UrlRepositoryImpl extends DomainRepositoryImpl<Url> implements UrlRepository {

    private static final Logger log = LoggerFactory.getLogger(UrlRepositoryImpl.class);
    
    @Override
    public Url findByDisplayExpandedUrl(String display, String expanded, String url) {
        try {
            String name = "Url.findByDisplayExpandedUrl";
            TypedQuery<Url> query = entityManager.createNamedQuery(name, Url.class);
            query.setParameter("display", display);
            query.setParameter("expanded", expanded);
            query.setParameter("url", url);
            Url result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + display);
            log.info("not found: " + expanded);
            log.info("not found: " + url);
            throw e;
        }
    }

    @Override
    public Url findByUrl(String url) {
        try {
            String name = "Url.findByUrl";
            TypedQuery<Url> query = entityManager.createNamedQuery(name, Url.class);
            query.setParameter("url", url);
            Url result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + url);
            throw e;
        }
    }
}
