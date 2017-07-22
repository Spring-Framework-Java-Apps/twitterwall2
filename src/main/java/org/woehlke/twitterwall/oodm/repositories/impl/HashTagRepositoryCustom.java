package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.HashTag;

public interface HashTagRepositoryCustom {

    HashTag findByUniqueId(HashTag domainObject);
}
