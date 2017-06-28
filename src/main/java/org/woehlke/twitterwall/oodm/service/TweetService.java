package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetService extends DomainService {

    Tweet findByIdTwitter(long idTwitter);

    Tweet persist(Tweet myTweet);

    Tweet update(Tweet myTweet);

    List<Tweet> getLatestTweets();

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);

    long count();

    List<Tweet> getTestTweetsForTweetTest();

    List<Tweet> getTweetsForUser(User user);

    List<Long> getAllTwitterIds();

    Tweet store(Tweet tweet);

    Tweet transformTweet(org.springframework.social.twitter.api.Tweet tweet);

    //Tweet storeTweet(Tweet tweet);
    
}
