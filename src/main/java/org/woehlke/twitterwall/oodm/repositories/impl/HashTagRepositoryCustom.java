package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface HashTagRepositoryCustom extends DomainObjectMinimalRepository<HashTag> {

    HashTag findByUniqueId(HashTag domainObject);
}
