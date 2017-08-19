package org.woehlke.twitterwall.backend.service.transform.common;

import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.common.DomainObject;

/**
 * Created by tw on 28.06.17.
 */
public interface TransformService<T extends DomainObject,SRC> {

    T transform(SRC twitterObject, Task task);
}
