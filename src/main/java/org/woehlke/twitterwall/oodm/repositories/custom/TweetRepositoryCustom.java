package org.woehlke.twitterwall.oodm.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectWithEntitiesRepository;

public interface TweetRepositoryCustom extends DomainObjectWithEntitiesRepository<Tweet> {

    Tweet findByUniqueId(Tweet domainObject);

    Page<Object2Entity> findAllTweet2HashTag(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Media(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Mention(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Url(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2TickerSymbol(Pageable pageRequest);
}
