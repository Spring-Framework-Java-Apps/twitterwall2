package org.woehlke.twitterwall.process;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.MyTweet;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;

/**
 * Created by tw on 11.06.17.
 */
public interface StoreTweetsProcess {
    MyTweet storeOneTweet(Tweet tweet);

    MyTwitterProfile storeFollower(TwitterProfile follower);

    MyTwitterProfile storeFriend(TwitterProfile friend);
}
