package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.Task;

/**
 * Created by tw on 09.07.17.
 */
public interface StoreOneTweet {

    Tweet storeOneTweet(org.springframework.social.twitter.api.Tweet tweet, Task task);
}
