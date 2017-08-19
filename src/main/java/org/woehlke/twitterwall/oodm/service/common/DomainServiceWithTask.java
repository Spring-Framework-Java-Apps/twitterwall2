package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.common.DomainObjectWithTask;

/**
 * Created by tw on 14.07.17.
 */
public interface DomainServiceWithTask<T extends DomainObjectWithTask> extends DomainService<T> {

    T store(T domainObject, Task task);

    T create(T domainObject, Task task);

    T update(T domainObject, Task task);
}
