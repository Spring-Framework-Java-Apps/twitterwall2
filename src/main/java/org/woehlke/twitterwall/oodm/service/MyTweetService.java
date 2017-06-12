package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.MyTweet;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface MyTweetService {

    MyTweet findByIdTwitter(long idTwitter);

    MyTweet persist(MyTweet myTweet);

    MyTweet update(MyTweet myTweet);

    List<MyTweet> getLatestTweets();
}
