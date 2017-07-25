package org.woehlke.twitterwall.oodm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;

import java.util.List;


/**
 * Created by tw on 10.06.17.
 */
public interface TweetService extends DomainServiceWithIdTwitter<Tweet>,DomainServiceWithTask<Tweet> {

    Page<Tweet> findTweetsForHashTag(HashTag hashtag, Pageable pageRequest);

    Page<Tweet> findTweetsForUser(User user, Pageable pageRequest);

    Page<Long> findAllTwitterIds(Pageable pageRequest);

}
