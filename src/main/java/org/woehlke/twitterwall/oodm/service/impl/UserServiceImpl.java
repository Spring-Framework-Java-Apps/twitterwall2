package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.repository.UserRepository;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

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
    public User create(User user) {
        return userRepository.persist(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll(User.class);
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
    public List<User> getTweetingUsers() {
        return userRepository.getTweetingUsers();
    }

    @Override
    public List<User> getNotYetFriendUsers() {
        return userRepository.getNotYetFriendUsers();
    }

    @Override
    public List<User> getNotYetOnList() {
        return userRepository.getNotYetOnList();
    }

    @Override
    public List<User> getOnList() {
        return userRepository.getOnList();
    }

    @Override
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter,User.class);
    }

    @Override
    public List<String> getAllDescriptions() {
        return userRepository.getAllDescriptions();
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return userRepository.getAllTwitterIds();
    }

    @Override
    public List<User> getUsersForHashTag(String hashtagText) {
        List<User> users = userRepository.getUsersForHashTag(hashtagText);
        return users;
    }

    @Override
    public long countUsersForHashTag(String hashtagText) {
        long numberUsers = userRepository.countUsersForHashTag(hashtagText);
        return numberUsers;
    }
}
