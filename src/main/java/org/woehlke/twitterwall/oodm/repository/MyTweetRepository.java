package org.woehlke.twitterwall.oodm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyTweet;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
public interface MyTweetRepository {

    MyTweet findByIdTwitter(long idTwitter);

    MyTweet persist(MyTweet myTweet);

    MyTweet update(MyTweet myTweet);

    List<MyTweet> getLatestTweets();

}
