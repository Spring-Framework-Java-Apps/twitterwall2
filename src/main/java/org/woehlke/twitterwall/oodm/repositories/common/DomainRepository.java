package org.woehlke.twitterwall.oodm.repositories.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

@NoRepositoryBean
public interface DomainRepository<T extends DomainObject> extends DomainObjectMinimalRepository<T>,PagingAndSortingRepository<T,Long> {
}
