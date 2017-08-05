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
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UserRepository;
import org.woehlke.twitterwall.oodm.service.UserService;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserServiceImpl extends DomainServiceWithTaskImpl<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        super(userRepository,taskRepository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public User findByScreenName(String screenName) {
        if (!User.isValidScreenName(screenName)) {
            return null;
        }
        return userRepository.findByScreenName(screenName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public User findByidTwitterAndScreenNameUnique(long idTwitter, String screenNameUnique) {
        return userRepository.findByidTwitterAndScreenNameUnique(idTwitter,screenNameUnique);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getTweetingUsers(Pageable pageRequest) {
        return userRepository.findTweetingUsers(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getNotYetFriendUsers(Pageable pageRequest) {
        return userRepository.findNotYetFriendUsers(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getNotYetOnList(Pageable pageRequest) {
        return userRepository.findNotYetOnList(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getOnList(Pageable pageRequest) {
        return userRepository.findOnList(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public User findByIdTwitter(long idTwitter) {
        return userRepository.findByIdTwitter(idTwitter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<String> getAllDescriptions(Pageable pageRequest) {
        return userRepository.findAllDescriptions(pageRequest);
    }

    /*
    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        return userRepository.findAllTwitterIds(pageRequest);
    }
    */

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getUsersForHashTag(HashTag hashTag, Pageable pageRequest) {
        return userRepository.findUsersForHashTag(hashTag.getText(),pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getFriends(Pageable pageRequest) {
        return userRepository.findFriendUsers(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getFollower(Pageable pageRequest) {
        return userRepository.findFollower(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<User> getNotYetFollower(Pageable pageRequest) {
        return userRepository.findNotYetFollower(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest) {
        return userRepository.findAllUser2HashTag(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<Object2Entity> findAllUser2Media(Pageable pageRequest) {
        return userRepository.findAllUser2Media(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest) {
        return userRepository.findAllUser2Mentiong(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<Object2Entity> findAllUser2Url(Pageable pageRequest) {
        return userRepository.findAllUser2Url(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest){
        return userRepository.findAllUser2TickerSymbol(pageRequest);
    }

}
