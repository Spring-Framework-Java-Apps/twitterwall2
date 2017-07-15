package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.dao.UrlDao;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class UrlDaoImpl extends DomainDaoImpl<Url> implements UrlDao {

    private static final Logger log = LoggerFactory.getLogger(UrlDaoImpl.class);

    @Override
    public Url findByUrl(String url) {
        String name = "Url.findByUrl";
        log.debug(name);
        try {
            TypedQuery<Url> query = entityManager.createNamedQuery(name, Url.class);
            query.setParameter("url", url);
            Url result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + url);
            throw e;
        }
    }
}
