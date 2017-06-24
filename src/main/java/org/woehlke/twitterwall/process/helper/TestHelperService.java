package org.woehlke.twitterwall.process.helper;

/**
 * Created by tw on 20.06.17.
 */
public interface TestHelperService {

    void fetchTweetsFromTwitterSearchTest(long[] idTwitterToFetch);

    String performTweetTest(long idTwitter, String output, boolean retweet);

    void waitForImport();
}
