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
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.dao.UserDao;
import org.woehlke.twitterwall.oodm.repositories.UserRepository;
import org.woehlke.twitterwall.oodm.service.UserService;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    @Override
    public User store(User user, Task task) {
        String name = "try to store: "+user.getIdTwitter()+" ";
        log.debug(name);
        try {
            User userPersistent = userDao.findByIdTwitter(user.getIdTwitter(),User.class);
            user.setCreatedBy(userPersistent.getCreatedBy());
            user.setId(userPersistent.getId());
            user.setUpdatedBy(task);
            user = userDao.update(user);
            log.debug(name+" updated "+user.toString());
            return user;
        } catch (EmptyResultDataAccessException e) {
            user.setCreatedBy(task);
            user = userDao.persist(user);
            log.debug(name+" persisted "+user.toString());
            return user;
        }
    }

    @Override
    public User create(User user, Task task) {
        user.setCreatedBy(task);
        return userRepository.save(user);
        //return userDao.persist(user);
    }

    @Override
    public User update(User user, Task task) {
        user.setUpdatedBy(task);
        return userRepository.save(user);
        //return userDao.update(user);
    }

    @Override
    public Page<User> getAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
        //return userDao.getAll(User.class,pageRequest);
    }

    @Override
    public long count() {
        return userRepository.count();
        //return userDao.count(User.class);
    }

    @Override
    public User findByScreenName(String screenName) {
        if (!User.isValidScreenName(screenName)) {
            throw new IllegalArgumentException("User.isValidScreenName("+screenName+") = false" );
        }
        return userDao.findByScreenName(screenName);
    }

    @Override
    public Page<User> getTweetingUsers(Pageable pageRequest) {
        return userDao.getTweetingUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetFriendUsers(Pageable pageRequest) {
        return userDao.getNotYetFriendUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetOnList(Pageable pageRequest) {
        return userDao.getNotYetOnList(pageRequest);
    }

    @Override
    public Page<User> getOnList(Pageable pageRequest) {
        return userDao.getOnList(pageRequest);
    }

    @Override
    public User findByIdTwitter(long idTwitter) {
        return userDao.findByIdTwitter(idTwitter,User.class);
    }

    @Override
    public Page<String> getAllDescriptions(Pageable pageRequest) {
        return userDao.getAllDescriptions(pageRequest);
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        return userDao.getAllTwitterIds(pageRequest);
    }

    @Override
    public Page<User> getUsersForHashTag(String hashtagText,Pageable pageRequest) {
        Page<User> users = userDao.getUsersForHashTag(hashtagText,pageRequest);
        return users;
    }

    @Override
    public long countUsersForHashTag(String hashtagText) {
        long numberUsers = userDao.countUsersForHashTag(hashtagText);
        return numberUsers;
    }
}
