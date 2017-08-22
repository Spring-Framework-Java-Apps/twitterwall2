package org.woehlke.twitterwall.oodm.repositories.common;

import org.woehlke.twitterwall.oodm.model.common.DomainObjectMinimal;

public interface DomainObjectMinimalRepository<T extends DomainObjectMinimal> {
    T findByUniqueId(T domainObject);
}
