package org.woehlke.twitterwall.oodm.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.transients.HashTagCounted;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface HashTagRepositoryCustom extends DomainObjectEntityRepository<HashTag> {

    HashTag findByUniqueId(HashTag domainObject);

    Page<HashTagCounted> countAllUser2HashTag(Pageable pageRequest);

    Page<HashTagCounted> countAllTweet2HashTag(Pageable pageRequest);
}
