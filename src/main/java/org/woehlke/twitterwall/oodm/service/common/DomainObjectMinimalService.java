package org.woehlke.twitterwall.oodm.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.common.DomainObjectMinimal;

public interface DomainObjectMinimalService<T extends DomainObjectMinimal> {

    Page<T> getAll(Pageable pageRequest);

    long count();
}
