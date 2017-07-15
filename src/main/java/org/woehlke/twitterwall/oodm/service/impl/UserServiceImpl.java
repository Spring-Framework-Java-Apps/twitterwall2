package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.repository.UserRepository;
import org.woehlke.twitterwall.oodm.service.UserService;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User store(User user, Task task) {
        String name = "try to store: "+user.getIdTwitter()+" ";
        log.debug(name);
        try {
            User userPersistent = userRepository.findByIdTwitter(user.getIdTwitter(),User.class);
            user.setCreatedBy(userPersistent.getCreatedBy());
            user.setId(userPersistent.getId());
            user.setUpdatedBy(task);
            user = userRepository.update(user);
            log.debug(name+" updated "+user.toString());
            return user;
        } catch (EmptyResultDataAccessException e) {
            user.setCreatedBy(task);
            user = userRepository.persist(user);
            log.debug(name+" persisted "+user.toString());
            return user;
        }
    }

    @Override
    public User create(User user, Task task) {
        user.setCreatedBy(task);
        return userRepository.persist(user);
    }

    @Override
    public User update(User user, Task task) {
        user.setUpdatedBy(task);
        return userRepository.update(user);
    }

    @Override
    public Page<User> getAll(Pageable pageRequest) {
        return userRepository.getAll(User.class,pageRequest);
    }

    @Override
    public long count() {
        return userRepository.count(User.class);
    }

    @Override
    public User findByScreenName(String screenName) {
        if (!User.isValidScreenName(screenName)) {
            throw new IllegalArgumentException("User.isValidScreenName("+screenName+") = false" );
        }
        return userRepository.findByScreenName(screenName);
    }

    @Override
    public Page<User> getTweetingUsers(Pageable pageRequest) {
        return userRepository.getTweetingUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetFriendUsers(Pageable pageRequest) {
        return userRepository.getNotYetFriendUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetOnList(Pageable pageRequest) {
        return userRepository.getNotYetOnList(pageRequest);
    }

    @Override
    public Page<User> getOnList(Pageable pageRequest) {
        return userRepository.getOnList(pageRequest);
    }

    @Override
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter,User.class);
    }

    @Override
    public Page<String> getAllDescriptions(Pageable pageRequest) {
        return userRepository.getAllDescriptions(pageRequest);
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        return userRepository.getAllTwitterIds(pageRequest);
    }

    @Override
    public Page<User> getUsersForHashTag(String hashtagText,Pageable pageRequest) {
        Page<User> users = userRepository.getUsersForHashTag(hashtagText,pageRequest);
        return users;
    }

    @Override
    public long countUsersForHashTag(String hashtagText) {
        long numberUsers = userRepository.countUsersForHashTag(hashtagText);
        return numberUsers;
    }
}
