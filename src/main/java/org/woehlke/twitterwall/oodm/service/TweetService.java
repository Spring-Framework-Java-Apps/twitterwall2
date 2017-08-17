package org.woehlke.twitterwall.oodm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.transients.*;
import org.woehlke.twitterwall.oodm.service.common.DomainObjectWithEntitiesService;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;


/**
 * Created by tw on 10.06.17.
 */
public interface TweetService extends DomainObjectWithEntitiesService<Tweet>,DomainServiceWithIdTwitter<Tweet>,DomainServiceWithTask<Tweet> {

    Page<Tweet> findTweetsForHashTag(HashTag hashtag, Pageable pageRequest);

    Page<Tweet> findTweetsForUser(User user, Pageable pageRequest);

    Page<Object2Entity> findAllTweet2HashTag(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Media(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Mention(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2Url(Pageable pageRequest);

    Page<Object2Entity> findAllTweet2TickerSymbol(Pageable pageRequest);

    Page<Tweet> getHomeTimeline(Pageable pageRequest);

    Page<Tweet> getUserTimeline(Pageable pageRequest);

    Page<Tweet> getMentions(Pageable pageRequest);

    Page<Tweet> getFavorites(Pageable pageRequest);

    Page<Tweet> getRetweetsOfMe(Pageable pageRequest);

}
