package org.woehlke.twitterwall.oodm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyTweet;

/**
 * Created by tw on 10.06.17.
 */
@Repository
public interface MyTweetRepository extends CrudRepository<MyTweet, Long> {
}
