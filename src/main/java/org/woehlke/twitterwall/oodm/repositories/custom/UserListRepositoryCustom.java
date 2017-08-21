package org.woehlke.twitterwall.oodm.repositories.custom;

import org.springframework.data.jpa.repository.Query;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface UserListRepositoryCustom extends DomainObjectMinimalRepository<UserList> {

    UserList findByUniqueId(UserList domainObject);

    @Query(name="UserList.countUserList2Subcriber",nativeQuery=true)
    long countUserList2Subcriber();

    @Query(name="UserList.countUserList2Members",nativeQuery=true)
    long countUserList2Members();
}
