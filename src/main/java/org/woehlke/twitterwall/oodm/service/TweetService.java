package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetService extends DomainServiceWithIdTwitter<Tweet>,DomainServiceWithTask<Tweet> {

    List<Tweet> getLatestTweets();

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);

    List<Tweet> getTweetsForUser(User user);

    List<Long> getAllTwitterIds();

}
