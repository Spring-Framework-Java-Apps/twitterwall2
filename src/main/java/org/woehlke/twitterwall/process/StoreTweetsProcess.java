package org.woehlke.twitterwall.process;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 11.06.17.
 */
public interface StoreTweetsProcess {

    Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet tweet);

    User storeFollower(TwitterProfile follower);

    User storeFriend(TwitterProfile friend);

    User storeUserProfile(TwitterProfile userProfile);
}
