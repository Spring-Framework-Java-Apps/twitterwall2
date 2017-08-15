package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.repositories.custom.UserListRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserListRepositoryImpl implements UserListRepositoryCustom {

    @Override
    public UserList findByUniqueId(UserList domainObject) {
        String name="UserList.findByUniqueId";
        TypedQuery<UserList> query = entityManager.createNamedQuery(name,UserList.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        List<UserList> resultList = query.getResultList();
        if(resultList.size()>0){
            return resultList.iterator().next();
        } else {
            return null;
        }
    }

    private final EntityManager entityManager;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserListRepositoryImpl(EntityManager entityManager,DataSource dataSource) {
        this.entityManager = entityManager;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
