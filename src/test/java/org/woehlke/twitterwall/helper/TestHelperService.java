package org.woehlke.twitterwall.helper;

/**
 * Created by tw on 20.06.17.
 */
public interface TestHelperService {

    void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch);
    
    void performTweetTest(long idTwitter,String output, boolean retweet);
}
