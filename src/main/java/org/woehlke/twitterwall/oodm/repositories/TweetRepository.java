package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.TweetRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TweetRepository extends DomainRepository<Tweet>,TweetRepositoryCustom {

    Tweet findByIdTwitter(long idTwitter);

    Page<Tweet> findByUser(User user, Pageable pageRequest);

    @Query(
        name="Tweet.getTweetsForHashTag",
        countName="Tweet.countTweetsForHashTag"
    )
    Page<Tweet> findByHashTag(@Param("hashtagText") String hashtagText, Pageable pageRequest);

    @Query(name = "Tweet.findTweetsForMedia")
    Page<Tweet> findTweetsForMedia(Media media, Pageable pageRequestTweet);

    @Query(name = "Tweet.findTweetsForMention")
    Page<Tweet> findTweetsForMention(Mention mention, Pageable pageRequestTweet);

    @Query(name = "Tweet.findTweetsForUrl")
    Page<Tweet> findTweetsForUrl(Url url, Pageable pageRequestTweet);

    @Query(name = "Tweet.findTweetsForTickerSymbol")
    Page<Tweet> findTweetsForTickerSymbol(TickerSymbol tickerSymbol, Pageable pageRequestTweet);

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

    @Query(name="Tweet.getHomeTimeline",nativeQuery=true)
    Page<Tweet> getHomeTimeline(Pageable pageRequest);

    @Query(name="Tweet.getUserTimeline",nativeQuery=true)
    Page<Tweet> getUserTimeline(Pageable pageRequest);

    @Query(name="Tweet.getMentions",nativeQuery=true)
    Page<Tweet> getMentions(Pageable pageRequest);

    @Query(name="Tweet.getFavorites",nativeQuery=true)
    Page<Tweet> getFavorites(Pageable pageRequest);

    @Query(name="Tweet.getRetweetsOfMe",nativeQuery=true)
    Page<Tweet> getRetweetsOfMe(Pageable pageRequest);

}
