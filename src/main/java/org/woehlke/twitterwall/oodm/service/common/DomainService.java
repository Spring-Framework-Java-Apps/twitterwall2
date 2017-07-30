package org.woehlke.twitterwall.oodm.service.common;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainService<T extends DomainObject> extends DomainObjectMinimalService<T> {
    T store(T domainObject, Task task);
}
