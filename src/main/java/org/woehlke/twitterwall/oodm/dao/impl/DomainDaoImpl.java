package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tw on 28.06.17.
 */
public class DomainDaoImpl<T extends DomainObject> implements DomainDao<T> {

    private static final Logger log = LoggerFactory.getLogger(DomainDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T persist(T domainObject) {
        log.debug("try to persist: "+domainObject.toString());
        entityManager.persist(domainObject);
        entityManager.flush();
        log.debug("persisted: "+domainObject.toString());
        return domainObject;
    }

    @Override
    public T update(T domainObject) {
        log.debug("try to merge: "+domainObject.toString());
        domainObject = entityManager.merge(domainObject);
        entityManager.flush();
        log.debug("merged: "+domainObject.toString());
        return domainObject;
    }

    @Override
    public Page<T> getAll(Class<T> myClass, Pageable pageRequest) {
        String name = myClass.getSimpleName() + ".getAll";
        log.debug(name);
        TypedQuery<T> query = entityManager.createNamedQuery(name, myClass);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<T> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+total);
        return resultPage;
    }

    @Override
    public long count(Class<T> myClass) {
        String name = myClass.getSimpleName() + ".count";
        log.debug(name);
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        long result = query.getSingleResult();
        log.debug(name+"  "+result);
        return result;
    }
}
