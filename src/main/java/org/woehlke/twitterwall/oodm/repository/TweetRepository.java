package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetRepository extends OodmRepository {

    Tweet findByIdTwitter(long idTwitter);

    Tweet persist(Tweet myTweet);

    Tweet update(Tweet myTweet);

    List<Tweet> getLatestTweets();

    boolean isNotYetStored(Tweet tweet);

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);

    long count();

    List<Tweet> getTweetsForUser(User user);

    List<Long> getAllTwitterIds();
}
