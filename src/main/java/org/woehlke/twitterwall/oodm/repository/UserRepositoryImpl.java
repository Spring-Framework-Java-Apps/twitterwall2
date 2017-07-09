package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.User;
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
        String name = "User.findByScreenName";
        try {
            TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
            query.setParameter("screenName", screenName);
            User result = query.getSingleResult();
            log.debug(name+" found: " + screenName);
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + screenName);
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
    public List<User> getNotYetOnList() {
        String name = "User.getNotYetOnList";
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getOnList() {
        String name = "User.getOnList";
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

    @Override
    public List<User> getUsersForHashTag(String hashtagText) {
        String name = "User.getUsersForHashTag";
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        query.setParameter("hashtagText", hashtagText);
        return query.getResultList();
    }

    @Override
    public long countUsersForHashTag(String hashtagText) {
        long usersForHashTag = 0L;
        String name = "User.countUsersForHashTag";
        try {
            TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
            query.setParameter("hashtagText", hashtagText);
            usersForHashTag = query.getSingleResult();
            log.debug(name+" found: " + hashtagText);
            return usersForHashTag;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + hashtagText);
            throw e;
        }
    }


}
