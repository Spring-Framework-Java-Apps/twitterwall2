package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.HashTagText;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.custom.TweetRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TweetRepository extends DomainRepository<Tweet>,TweetRepositoryCustom {

    Tweet findByIdTwitter(long idTwitter);

    @Query(
        name="Tweet.getTweetsForHashTag",
        countName="Tweet.countTweetsForHashTag"
    )
    Page<Tweet> findByHashTag(@Param("hashtagText") String hashtagText, Pageable pageRequest);

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
