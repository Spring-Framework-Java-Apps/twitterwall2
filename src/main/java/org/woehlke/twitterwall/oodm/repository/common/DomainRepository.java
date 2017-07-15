package org.woehlke.twitterwall.oodm.repository.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainRepository<T extends DomainObject> {

    T persist(T domainObject);

    T update(T domainObject);

    Page<T> getAll(Class<T> myClass, Pageable pageRequest);

    long count(Class<T> myClass);
}
