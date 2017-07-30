package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectMinimal;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractDomainRepositoryImpl<T extends DomainObjectMinimal> implements DomainObjectMinimalRepository<T> {

    protected final EntityManager entityManager;

    protected AbstractDomainRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T findByUniqueId(T domainObject) {
        if(domainObject == null){
            return null;
        }
        if(domainObject.isValid()){
            return null;
        }
        String name= domainObject.getQueryNameForFindByUniqueId();
        TypedQuery<? extends DomainObjectMinimal> query = entityManager.createNamedQuery(name,domainObject.getClass());
        for(String key:domainObject.getParametersForFindByUniqueId().keySet()){
            query.setParameter(key,domainObject.getParametersForFindByUniqueId().get(key));
        }
        List<? extends DomainObjectMinimal> resultList = query.getResultList();
        if(resultList.size()>0){
            return (T) resultList.iterator().next();
        } else {
            return null;
        }
    }
}
