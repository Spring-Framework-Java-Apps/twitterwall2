package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.User;

public interface UserRepositoryCustom {

    User findByUniqueId(User domainObject);
}
