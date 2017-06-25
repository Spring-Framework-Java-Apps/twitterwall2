package org.woehlke.twitterwall.process.backend;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
public interface TwitterApiService {

    List<Tweet> findTweetsForSearchQuery();

    Tweet findOneTweetById(long id);

    TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId);

    TwitterProfile getUserProfileForScreenName(String screenName);
}
