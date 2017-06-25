package org.woehlke.twitterwall.oodm.service;

/**
 * Created by tw on 25.06.17.
 */
public interface TweetApiServiceTest {
    
    String performTweetTest(long idTwitter, String output, boolean retweet);

    void waitForImport();
}
