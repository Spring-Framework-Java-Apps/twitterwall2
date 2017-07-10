package org.woehlke.twitterwall.oodm.entities.common;

import java.io.Serializable;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainObject<T extends DomainObject> extends Serializable, Comparable<T>  {

    Long getId();

    void setId(Long id);

    boolean equals(T o);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
