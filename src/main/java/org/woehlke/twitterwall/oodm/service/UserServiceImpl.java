package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.UserRepository;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public User persist(User user) {
        return userRepository.persist(user);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getFollower() {
        return userRepository.getFollower();
    }

    @Override
    public List<User> getFriends() {
        return userRepository.getFriends();
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User findByScreenName(String screenName) {
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
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = true)
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter);
    }
}
