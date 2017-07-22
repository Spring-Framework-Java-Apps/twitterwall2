package org.woehlke.twitterwall.oodm.entities.common;

import java.io.Serializable;

public interface DomainObjectMinimal<T extends DomainObjectMinimal> extends Serializable, Comparable<T>{

    Long getId();

    void setId(Long id);

    String getUniqueId();

    boolean equals(T o);

    boolean equals(Object o);

    int hashCode();

    String toString();

    boolean isValid();

}
