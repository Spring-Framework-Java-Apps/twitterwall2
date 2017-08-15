package org.woehlke.twitterwall.oodm.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UserListRepositoryCustom;

public interface UserListRepository extends DomainRepository<UserList>,UserListRepositoryCustom {

    UserList findByIdTwitter(long idTwitter);
}
