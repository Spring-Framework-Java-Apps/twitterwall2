package org.woehlke.twitterwall.scheduled.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import java.io.Serializable;

/**
 * Created by tw on 28.06.17.
 */
public interface TransformService<T extends DomainObject,SRC extends Serializable> {

    T transform(SRC twitterObject);
}
