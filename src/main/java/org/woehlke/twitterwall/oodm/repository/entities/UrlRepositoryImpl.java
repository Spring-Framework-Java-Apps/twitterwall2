package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class UrlRepositoryImpl extends DomainRepositoryImpl<Url> implements UrlRepository {

    private static final Logger log = LoggerFactory.getLogger(UrlRepositoryImpl.class);

    @Override
    public Url findByUrl(String url) {
        String name = "Url.findByUrl";
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
