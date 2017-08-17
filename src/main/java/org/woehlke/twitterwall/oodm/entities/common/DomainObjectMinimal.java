package org.woehlke.twitterwall.oodm.entities.common;

import java.io.Serializable;

public interface DomainObjectMinimal<T extends DomainObjectMinimal> extends DomainObjectWithUniqueId,DomainObjectWithValidation,Serializable, Comparable<T>{

    Long getId();

    void setId(Long id);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
