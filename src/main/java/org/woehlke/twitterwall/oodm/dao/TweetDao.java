package org.woehlke.twitterwall.oodm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithIdTwitter;


/**
 * Created by tw on 10.06.17.
 */
public interface TweetDao extends DomainDaoWithIdTwitter<Tweet> {

    Page<Tweet> getLatestTweets(Pageable pageRequest);

    Page<Tweet> getTweetsForHashTag(String hashtagText,Pageable pageRequest);

    long countTweetsForHashTag(String hashtagText);

    Page<Tweet> getTweetsForUser(User user, Pageable pageRequest);

    Page<Long> getAllTwitterIds(Pageable pageRequest);
}
