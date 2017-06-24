package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByIdTwitterException;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByScreenNameException;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from User as t where t.idTwitter=:idTwitter";
            TypedQuery<User> query = entityManager.createQuery(SQL, User.class);
            query.setParameter("idTwitter", idTwitter);
            User result = query.getSingleResult();
            log.debug("found: " + result.getIdTwitter());
            return result;
        } catch (NoResultException e) {
            log.debug("not found: " + idTwitter);
            throw new FindUserByIdTwitterException(e, idTwitter);
        }
    }

    @Override
    public User persist(User user) {
        entityManager.persist(user);
        user = findByIdTwitter(user.getIdTwitter());
        log.info("persisted: " + user.toString());
        return user;
    }

    @Override
    public User update(User user) {
        user = entityManager.merge(user);
        user = findByIdTwitter(user.getIdTwitter());
        log.info("updated: " + user.toString());
        return user;
    }

    @Override
    public List<User> getAll() {
        String SQL = "select t from User as t";
        TypedQuery<User> query = entityManager.createQuery(SQL, User.class);
        return query.getResultList();
    }

    @Override
    public User findByScreenName(String screenName) {
        try {
            String SQL = "select t from User as t where t.screenName=:screenName";
            TypedQuery<User> query = entityManager.createQuery(SQL, User.class);
            query.setParameter("screenName", screenName);
            User result = query.getSingleResult();
            log.debug("found: " + result.getIdTwitter());
            return result;
        } catch (NoResultException e) {
            log.debug("not found: " + screenName);
            throw new FindUserByScreenNameException(e, screenName);
        }
    }

    @Override
    public List<User> getTweetingUsers() {
        String SQL = "select t from User as t where t.tweeting=true";
        TypedQuery<User> query = entityManager.createQuery(SQL, User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getNotYetFriendUsers() {
        String SQL = "select t from User as t where t.following=false";
        TypedQuery<User> query = entityManager.createQuery(SQL, User.class);
        return query.getResultList();
    }

    @Override
    public List<String> getAllDescriptions() {
        String SQL = "select t.description from User as t where t.description is not null";
        TypedQuery<String> query = entityManager.createQuery(SQL, String.class);
        return query.getResultList();
    }

    @Override
    public List<Long> getAllTwitterIds() {
        String SQL = "select t.idTwitter from User as t";
        TypedQuery<Long> query = entityManager.createQuery(SQL, Long.class);
        return query.getResultList();
    }
}
