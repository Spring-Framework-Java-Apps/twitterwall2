package org.woehlke.twitterwall.oodm.repositories;


import org.springframework.data.jpa.repository.Query;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UserListRepositoryCustom;

import java.util.List;

public interface UserListRepository extends DomainRepository<UserList>,UserListRepositoryCustom {

    UserList findByIdTwitter(long idTwitter);

    @Query(name="UserList.countUserList2Subcriber",nativeQuery=true)
    long countUserList2Subcriber();

    @Query(name="UserList.countUserList2Members",nativeQuery=true)
    long countUserList2Members();
}
