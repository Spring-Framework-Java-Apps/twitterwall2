package org.woehlke.twitterwall.oodm.repositories;


import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UserListRepositoryCustom;

public interface UserListRepository extends DomainRepository<UserList>,UserListRepositoryCustom {

    UserList findByIdTwitter(long idTwitter);
}
