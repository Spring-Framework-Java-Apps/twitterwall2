package org.woehlke.twitterwall.oodm.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.transients.*;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectWithEntitiesRepository;

public interface UserRepositoryCustom extends DomainObjectWithEntitiesRepository<User> {

    User findByUniqueId(User domainObject);

    Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Media(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Url(Pageable pageRequest);

    Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest);

}
