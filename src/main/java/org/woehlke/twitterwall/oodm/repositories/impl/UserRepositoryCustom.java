package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface UserRepositoryCustom extends DomainObjectMinimalRepository<User> {

    User findByUniqueId(User domainObject);
}
