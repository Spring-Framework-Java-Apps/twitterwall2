package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repositories.custom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUniqueId(User domainObject) {
        String name="User.findByUniqueId";
        TypedQuery<User> query = entityManager.createNamedQuery(name,User.class);
        query.setParameter("idTwitter",domainObject.getIdTwitter());
        query.setParameter("screenName",domainObject.getScreenName());
        User result = query.getSingleResult();
        return result;
    }
}
