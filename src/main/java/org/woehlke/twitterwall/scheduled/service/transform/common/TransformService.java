package org.woehlke.twitterwall.scheduled.service.transform.common;

import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

/**
 * Created by tw on 28.06.17.
 */
public interface TransformService<T extends DomainObject,SRC> {

    T transform(SRC twitterObject, Task task);
}
