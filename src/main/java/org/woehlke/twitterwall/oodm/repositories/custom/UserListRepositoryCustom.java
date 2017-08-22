package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface UserListRepositoryCustom extends DomainObjectMinimalRepository<UserList> {

    UserList findByUniqueId(UserList domainObject);

}
