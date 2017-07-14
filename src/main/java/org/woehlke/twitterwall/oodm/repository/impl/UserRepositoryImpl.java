package org.woehlke.twitterwall.oodm.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.UserRepository;
import org.woehlke.twitterwall.oodm.repository.common.impl.DomainRepositoryWithIdTwitterImpl;

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
    public Page<User> getTweetingUsers(Pageable pageRequest) {
        String name = "User.getTweetingUsers";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<User> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<User> getNotYetFriendUsers(Pageable pageRequest) {
        String name = "User.getNotYetFriendUsers";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<User> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<User> getNotYetOnList(Pageable pageRequest) {
        String name = "User.getNotYetOnList";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<User> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<User> getOnList(Pageable pageRequest) {
        String name = "User.getOnList";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<User> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<String> getAllDescriptions(Pageable pageRequest) {
        String name= "User.getAllDescriptions";
        log.debug(name);
        TypedQuery<String> query = entityManager.createNamedQuery(name, String.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<String> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        String name ="User.getAllTwitterIds";
        log.debug(name);
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<Long> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
    }

    @Override
    public Page<User> getUsersForHashTag(String hashtagText, Pageable pageRequest) {
        String name = "User.getUsersForHashTag";
        log.debug(name);
        TypedQuery<User> query = entityManager.createNamedQuery(name, User.class);
        query.setParameter("hashtagText", hashtagText);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<User> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+result.size());
        return resultPage;
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
