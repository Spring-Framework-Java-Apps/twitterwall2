package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 15.07.17.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    User findByIdTwitter(long idTwitter);

    User findByScreenName(String screenName);

    @Query(name="User.findTweetingUsers")
    Page<User> findTweetingUsers(Pageable pageRequest);

    @Query(name="User.findNotYetFriendUsers")
    Page<User> findNotYetFriendUsers(Pageable pageRequest);

    @Query(name="User.findNotYetOnList")
    Page<User> findNotYetOnList(Pageable pageRequest);

    @Query(name="User.findOnList")
    Page<User> findOnList(Pageable pageRequest);

    @Query(name="User.findAllDescriptions")
    Page<String> findAllDescriptions(Pageable pageRequest);

    @Query(name="User.findAllTwitterIds")
    Page<Long> findAllTwitterIds(Pageable pageRequest);

    @Query(
        name="User.findUsersForHashTag"
    )
    Page<User>findUsersForHashTag(HashTag hashTag, Pageable pageRequest);

    //@Query(name="User.countUsersForHashTag",nativeQuery=true)
    //long countUsersForHashTag(String hashtagText);

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
