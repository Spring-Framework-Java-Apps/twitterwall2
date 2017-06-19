package org.woehlke.twitterwall.process;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
public interface TwitterApiService {

    List<Tweet> findTweetsForSearchQuery();

    List<Tweet> findTweetsForSearchQueryStartingWith(long id);

    Tweet findOneTweetById(long id);

    List<TwitterProfile>  getFollowers();

    List<TwitterProfile>  getFriends();
    
}
