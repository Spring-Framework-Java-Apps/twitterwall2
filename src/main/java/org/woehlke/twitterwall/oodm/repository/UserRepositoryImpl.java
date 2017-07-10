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
        log.debug(name);
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
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        List<User> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<User> getNotYetFriendUsers() {
        String name = "User.getNotYetFriendUsers";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        List<User> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<User> getNotYetOnList() {
        String name = "User.getNotYetOnList";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        List<User> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<User> getOnList() {
        String name = "User.getOnList";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        List<User> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<String> getAllDescriptions() {
        String name= "User.getAllDescriptions";
        log.debug(name);
        TypedQuery<String> query = entityManager.createNamedQuery(name, String.class);
        List<String> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<Long> getAllTwitterIds() {
        String name ="User.getAllTwitterIds";
        log.debug(name);
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        List<Long> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public List<User> getUsersForHashTag(String hashtagText) {
        String name = "User.getUsersForHashTag";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        query.setParameter("hashtagText", hashtagText);
        List<User> result = query.getResultList();
        log.debug(name+"  "+result.size());
        return result;
    }

    @Override
    public long countUsersForHashTag(String hashtagText) {
        long usersForHashTag = 0L;
        String name = "User.countUsersForHashTag";
        log.debug(name);
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
