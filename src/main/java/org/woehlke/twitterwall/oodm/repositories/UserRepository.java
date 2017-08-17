package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.UserRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface UserRepository extends DomainRepository<User>,UserRepositoryCustom {

    User findByIdTwitter(long idTwitter);

    User findByScreenName(String screenName);

    User findByidTwitterAndScreenNameUnique(long idTwitter, String screenNameUnique);

    @Query(name="User.findTweetingUsers")
    Page<User> findTweetingUsers(Pageable pageRequest);

    @Query(name="User.findFollower")
    Page<User> findFollower(Pageable pageRequest);

    @Query(name="User.findNotYetFollower")
    Page<User> findNotYetFollower(Pageable pageRequest);

    @Query(name="User.findFriends")
    Page<User> findFriendUsers(Pageable pageRequest);

    @Query(name="User.findNotYetFriends")
    Page<User> findNotYetFriendUsers(Pageable pageRequest);

    @Query(name="User.findNotYetOnList")
    Page<User> findNotYetOnList(Pageable pageRequest);

    @Query(name="User.findOnList")
    Page<User> findOnList(Pageable pageRequest);

    @Query(name="User.findAllDescriptions")
    Page<String> findAllDescriptions(Pageable pageRequest);

    @Query(
        name="User.getUsersForHashTag",
        countName = "User.countUsersForHashTag"
    )
    Page<User> findUsersForHashTag(@Param("hashtagText") String hashtagText, Pageable pageRequest);

    @Query(name="User.getUsersForMedia")
    Page<User> getUsersForMedia(Media media, Pageable pageRequestUser);

    @Query(name="User.getUsersForMention")
    Page<User> getUsersForMention(Mention mention, Pageable pageRequestUser);

    @Query(name="User.getUsersForUrl")
    Page<User> getUsersForUrl(Url url, Pageable pageRequestUser);

    @Query(name="User.getUsersForTickerSymbol")
    Page<User> getUsersForTickerSymbol(TickerSymbol tickerSymbol, Pageable pageRequestUser);

    @Query(name="User.findUsersWhoAreFriendsButNotFollowers")
    Page<User> findUsersWhoAreFriendsButNotFollowers(Pageable pageRequest);

    @Query(name="User.findUsersWhoAreFollowersAndFriends")
    Page<User> findUsersWhoAreFollowersAndFriends(Pageable pageRequest);

    @Query(name="User.findUsersWhoAreFollowersButNotFriends")
    Page<User> findUsersWhoAreFollowersButNotFriends(Pageable pageRequest);

    @Query(name="User.countAllUser2HashTag",nativeQuery=true)
    long countAllUser2HashTag();

    @Query(name="User.countAllUser2Media",nativeQuery=true)
    long countAllUser2Media();

    @Query(name="User.countAllUser2Mention",nativeQuery=true)
    long countAllUser2Mention();

    @Query(name="User.countAllUser2TickerSymbol",nativeQuery=true)
    long countAllUser2TickerSymbol();

    @Query(name="User.countAllUser2Url",nativeQuery=true)
    long countAllUser2Url();
}
