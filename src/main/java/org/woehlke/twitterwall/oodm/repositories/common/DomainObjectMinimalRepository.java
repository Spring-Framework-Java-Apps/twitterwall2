package org.woehlke.twitterwall.oodm.repositories.common;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectMinimal;

public interface DomainObjectMinimalRepository<T extends DomainObjectMinimal> extends PagingAndSortingRepository<T,Long> {
    T findByUniqueId(T domainObject);
}
