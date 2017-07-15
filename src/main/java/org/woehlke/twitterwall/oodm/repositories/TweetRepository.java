package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.Tweet;

/**
 * Created by tw on 15.07.17.
 */
public interface TweetRepository extends PagingAndSortingRepository<Tweet,Long> {
}
