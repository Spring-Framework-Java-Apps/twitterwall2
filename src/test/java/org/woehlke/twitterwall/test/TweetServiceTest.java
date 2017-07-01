package org.woehlke.twitterwall.test;

/**
 * Created by tw on 25.06.17.
 */
public interface TweetServiceTest {
    
    String performTweetTest(long idTwitter, String output, boolean retweet);

    void waitForImport();
}
