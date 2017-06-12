package org.woehlke.twitterwall.process;

import org.springframework.social.twitter.api.Tweet;
import org.woehlke.twitterwall.oodm.entities.MyTweet;

/**
 * Created by tw on 11.06.17.
 */
public interface StoreTweetsProcess {
    MyTweet storeOneTweet(Tweet tweet);
}
