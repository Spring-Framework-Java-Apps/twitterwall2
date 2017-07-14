package org.woehlke.twitterwall.oodm.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.common.DomainObject;

import java.util.List;

/**
 * Created by tw on 24.06.17.
 */
public interface DomainService<T extends DomainObject> {

    Page<T> getAll(Pageable pageRequest);

    long count();

}
