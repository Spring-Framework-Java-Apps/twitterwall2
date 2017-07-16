package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 15.07.17.
 */
public interface TweetRepository extends PagingAndSortingRepository<Tweet,Long> {

    Tweet findByIdTwitter(long idTwitter);

    /*
    @Query(name = "Tweet.findLatestTweets")
    Page<Tweet> findLatestTweets(Pageable pageRequest);
    */

    @Query(name="Tweet.findByHashTag")
    Page<Tweet> findByHashTag(HashTag hashTag, Pageable pageRequest);

    //@Query(name = "Tweet.findByUser")
    Page<Tweet> findByUser(User user, Pageable pageRequest);

    @Query(name = "Tweet.findAllTwitterIds")
    Page<Long> findAllTwitterIds(Pageable pageRequest);

    @Query(name="Tweet.countAllUser2HashTag",nativeQuery=true)
    long countAllUser2HashTag();

    @Query(name="Tweet.countAllUser2Media",nativeQuery=true)
    long countAllUser2Media();

    @Query(name="Tweet.countAllUser2Mention",nativeQuery=true)
    long countAllUser2Mention();

    @Query(name="Tweet.countAllUser2TickerSymbol",nativeQuery=true)
    long countAllUser2TickerSymbol();

    @Query(name="Tweet.countAllUser2Url",nativeQuery=true)
    long countAllUser2Url();

}
