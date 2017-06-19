package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Tweet;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetService {

    Tweet findByIdTwitter(long idTwitter);

    Tweet persist(Tweet myTweet);

    Tweet update(Tweet myTweet);

    List<Tweet> getLatestTweets();

    boolean isNotYetStored(Tweet tweet);

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);

    long count();

}
