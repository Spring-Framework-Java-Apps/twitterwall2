package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import java.util.List;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainService<T extends DomainObject> {

    T store(T domainObject);
    
    T create(T domainObject);

    T update(T domainObject);

    List<T> getAll();

    long count();
    
}
