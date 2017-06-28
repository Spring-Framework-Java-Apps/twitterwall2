package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByScreenNameException;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class UserRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<User> implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByScreenName(String screenName) {
        try {
            String name = "User.findByScreenName";
            TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
            query.setParameter("screenName", screenName);
            User result = query.getSingleResult();
            log.info("found: " + screenName);
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + screenName);
            throw e;
        }
    }

    @Override
    public List<User> getTweetingUsers() {
        String name = "User.getTweetingUsers";
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getNotYetFriendUsers() {
        String name = "User.getNotYetFriendUsers";
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        return query.getResultList();
    }

    @Override
    public List<String> getAllDescriptions() {
        String name= "User.getAllDescriptions";
        TypedQuery<String> query = entityManager.createNamedQuery(name, String.class);
        return query.getResultList();
    }

    @Override
    public List<Long> getAllTwitterIds() {
        String name ="User.getAllTwitterIds";
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        return query.getResultList();
    }
}
