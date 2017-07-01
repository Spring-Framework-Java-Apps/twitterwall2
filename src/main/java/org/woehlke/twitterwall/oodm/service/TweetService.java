package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface TweetService extends DomainService<Tweet> {

    Tweet findByIdTwitter(long idTwitter);
    
    List<Tweet> getLatestTweets();

    List<Tweet> getTweetsForHashTag(String hashtagText);

    long countTweetsForHashTag(String hashtagText);
    
    List<Tweet> getTweetsForUser(User user);

    List<Long> getAllTwitterIds();
    
}
