package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.repositories.UserRepository;
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
        User userPersistent = this.userRepository.findByIdTwitter(user.getIdTwitter());
        if(userPersistent!=null) {
            user.setCreatedBy(userPersistent.getCreatedBy());
            user.setId(userPersistent.getId());
            user.setUpdatedBy(task);
            user = this.userRepository.save(user);
            log.debug(name + " updated " + user.toString());
            return user;
        } else {
            user.setCreatedBy(task);
            user = this.userRepository.save(user);
            log.debug(name+" persisted "+user.toString());
            return user;
        }
    }

    @Override
    public User create(User user, Task task) {
        user.setCreatedBy(task);
        return userRepository.save(user);
    }

    @Override
    public User update(User user, Task task) {
        user.setUpdatedBy(task);
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAll(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return userRepository.count();
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
        return userRepository.findTweetingUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetFriendUsers(Pageable pageRequest) {
        return userRepository.findNotYetFriendUsers(pageRequest);
    }

    @Override
    public Page<User> getNotYetOnList(Pageable pageRequest) {
        return userRepository.findNotYetOnList(pageRequest);
    }

    @Override
    public Page<User> getOnList(Pageable pageRequest) {
        return userRepository.findOnList(pageRequest);
    }

    @Override
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Page<String> getAllDescriptions(Pageable pageRequest) {
        return userRepository.findAllDescriptions(pageRequest);
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        return userRepository.findAllTwitterIds(pageRequest);
    }

    @Override
    public Page<User> getUsersForHashTag(HashTag hashTag, Pageable pageRequest) {
        return userRepository.findUsersForHashTag(hashTag.getText(),pageRequest);
    }
}
