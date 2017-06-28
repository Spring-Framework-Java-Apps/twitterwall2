package org.woehlke.twitterwall.oodm.repository.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import java.util.List;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainRepository<T extends DomainObject> {

    T persist(T domainObject);

    T update(T domainObject);

    List<T> getAll(Class<T> myClass);

    long count(Class<T> myClass);
}
