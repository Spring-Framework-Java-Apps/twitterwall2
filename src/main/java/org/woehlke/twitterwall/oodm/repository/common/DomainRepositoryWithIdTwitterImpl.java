package org.woehlke.twitterwall.oodm.repository.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 28.06.17.
 */
public class DomainRepositoryWithIdTwitterImpl<T extends DomainObjectWithIdTwitter> extends DomainRepositoryImpl<T> implements DomainRepositoryWithIdTwitter<T> {

    private static final Logger log = LoggerFactory.getLogger(DomainRepositoryWithIdTwitterImpl.class);

    @Override
    public T findByIdTwitter(long idTwitter,Class<T> myClass) {
        String name = "Media.findByIdTwitter";
        TypedQuery<T> query = entityManager.createNamedQuery(name, myClass);
        query.setParameter("idTwitter", idTwitter);
        T result = query.getSingleResult();
        log.info("found: " + result.toString());
        return result;
    }
}
