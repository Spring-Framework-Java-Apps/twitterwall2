package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectWithIdTwitter;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithIdTwitter;

import javax.persistence.TypedQuery;

/**
 * Created by tw on 28.06.17.
 */
public class DomainDaoWithIdTwitterImpl<T extends DomainObjectWithIdTwitter> extends DomainDaoImpl<T> implements DomainDaoWithIdTwitter<T> {

    private static final Logger log = LoggerFactory.getLogger(DomainDaoWithIdTwitterImpl.class);

    @Override
    public T findByIdTwitter(long idTwitter,Class<T> myClass) {
        String name = myClass.getSimpleName()+ ".findByIdTwitter";
        log.debug(name);
        TypedQuery<T> query = entityManager.createNamedQuery(name, myClass);
        query.setParameter("idTwitter", idTwitter);
        T result = query.getSingleResult();
        log.debug(name+"found: " + result.toString());
        return result;
    }
}
