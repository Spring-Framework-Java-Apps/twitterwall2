package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.UserRepository;
import org.woehlke.twitterwall.oodm.service.UserService;


/**
 * Created by tw on 11.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl extends DomainServiceWithTaskImpl<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        super(userRepository,taskRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User findByScreenName(String screenName) {
        if (!User.isValidScreenName(screenName)) {
            return null;
        }
        return userRepository.findByScreenName(screenName);
    }

    @Override
    public User findByidTwitterAndScreenNameUnique(long idTwitter, String screenNameUnique) {
        return userRepository.findByidTwitterAndScreenNameUnique(idTwitter,screenNameUnique);
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
    public Page<User> findUsersWhoAreFriendsButNotFollowers(Pageable pageRequest) {
        return userRepository.findUsersWhoAreFriendsButNotFollowers(pageRequest);
    }

    @Override
    public Page<User> findUsersWhoAreFollowersAndFriends(Pageable pageRequest) {
        return userRepository.findUsersWhoAreFollowersAndFriends(pageRequest);
    }

    @Override
    public Page<User> findUsersWhoAreFollowersButNotFriends(Pageable pageRequest) {
        return userRepository.findUsersWhoAreFollowersButNotFriends(pageRequest);
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
    public Page<User> getUsersForHashTag(HashTag hashTag, Pageable pageRequest) {
        return userRepository.findUsersForHashTag(hashTag.getText(),pageRequest);
    }

    @Override
    public Page<User> getUsersForMedia(Media media, Pageable pageRequestUser) {
        return userRepository.getUsersForMedia(media, pageRequestUser);
    }

    @Override
    public Page<User> getUsersForMention(Mention mention, Pageable pageRequestUser) {
        return userRepository.getUsersForMention(mention, pageRequestUser);
    }

    @Override
    public Page<User> getUsersForUrl(Url url, Pageable pageRequestUser) {
        return userRepository.getUsersForUrl(url,pageRequestUser);
    }

    @Override
    public Page<User> getUsersForTickerSymbol(TickerSymbol tickerSymbol, Pageable pageRequestUser) {
        return userRepository.getUsersForTickerSymbol(tickerSymbol,pageRequestUser);
    }

    @Override
    public Page<User> getFriends(Pageable pageRequest) {
        return userRepository.findFriendUsers(pageRequest);
    }

    @Override
    public Page<User> getFollower(Pageable pageRequest) {
        return userRepository.findFollower(pageRequest);
    }

    @Override
    public Page<User> getNotYetFollower(Pageable pageRequest) {
        return userRepository.findNotYetFollower(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest) {
        return userRepository.findAllUser2HashTag(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2Media(Pageable pageRequest) {
        return userRepository.findAllUser2Media(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest) {
        return userRepository.findAllUser2Mentiong(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2Url(Pageable pageRequest) {
        return userRepository.findAllUser2Url(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest){
        return userRepository.findAllUser2TickerSymbol(pageRequest);
    }

    @Override
    public boolean isByIdTwitter(long userIdTwitter) {
        return ((userRepository.findByIdTwitter(userIdTwitter)) != null);
    }

    @Override
    public User findByUniqueId(User domainExampleObject) {
        return userRepository.findByUniqueId(domainExampleObject);
    }
}
