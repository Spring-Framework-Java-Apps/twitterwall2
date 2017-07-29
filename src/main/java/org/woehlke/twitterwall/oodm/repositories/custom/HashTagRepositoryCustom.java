package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface HashTagRepositoryCustom extends DomainObjectEntityRepository<HashTag> {

    HashTag findByUniqueId(HashTag domainObject);
}
