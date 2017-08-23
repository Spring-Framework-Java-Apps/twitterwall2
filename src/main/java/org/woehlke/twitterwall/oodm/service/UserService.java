package org.woehlke.twitterwall.oodm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.model.*;
import org.woehlke.twitterwall.oodm.model.transients.*;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectWithEntitiesService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithScreenName;

import java.util.List;


/**
 * Created by tw on 11.06.17.
 */
public interface UserService extends DomainObjectWithEntitiesService<User>,DomainServiceWithScreenName<User> {

    User findByidTwitterAndScreenNameUnique(long idTwitter, String screenNameUnique);

    Page<User> getTweetingUsers(Pageable pageRequest);

    Page<String> getAllDescriptions(Pageable pageRequest);

    Page<User> getUsersForHashTag(HashTag hashTag,Pageable pageRequest);

    Page<User> getUsersForMedia(Media media, Pageable pageRequestUser);

    Page<User> getUsersForMention(Mention mention, Pageable pageRequestUser);

    Page<User> getUsersForUrl(Url url, Pageable pageRequestUser);

    Page<User> getUsersForTickerSymbol(TickerSymbol tickerSymbol, Pageable pageRequestUser);

    Page<User> getFriends(Pageable pageRequest);

    Page<User> getNotYetFriendUsers(Pageable pageRequest);

    Page<User> getFollower(Pageable pageRequest);

    Page<User> getNotYetFollower(Pageable pageRequest);

    Page<User> getOnList(Pageable pageRequest);

    Page<User> getNotYetOnList(Pageable pageRequest);

    Page<User> findUsersWhoAreFriendsButNotFollowers(Pageable pageRequest);

    Page<User> findUsersWhoAreFollowersAndFriends(Pageable pageRequest);

    Page<User> findUsersWhoAreFollowersButNotFriends(Pageable pageRequest);

    Page<Object2Entity> findAllUser2HashTag(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Media(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Mentiong(Pageable pageRequest);

    Page<Object2Entity> findAllUser2Url(Pageable pageRequest);

    Page<Object2Entity> findAllUser2TickerSymbol(Pageable pageRequest);

    boolean isByIdTwitter(long userIdTwitter);

    Page<User> findUsersForUserList(UserList userList, Pageable pageRequest);

    List<Long> getIdTwitterOfAllUsers();
}
