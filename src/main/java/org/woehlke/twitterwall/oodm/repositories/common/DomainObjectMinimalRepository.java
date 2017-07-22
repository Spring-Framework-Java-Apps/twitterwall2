package org.woehlke.twitterwall.oodm.repositories.common;

import org.woehlke.twitterwall.oodm.entities.common.DomainObjectMinimal;

public interface DomainObjectMinimalRepository<T extends DomainObjectMinimal> {
    T findByUniqueId(T domainObject);
}
