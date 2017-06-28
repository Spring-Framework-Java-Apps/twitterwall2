package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitter;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetRepository extends DomainRepositoryWithIdTwitter<Tweet> {

    List<Tweet> getLatestTweets();

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);

    List<Tweet> getTweetsForUser(User user);

    List<Long> getAllTwitterIds();
}
