package org.woehlke.twitterwall.scheduled.service.transform;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.entities.Entities;

/**
 * Created by tw on 11.07.17.
 */
public interface EntitiesTransformService {

    Entities transformEntitiesForUser(TwitterProfile userFromTwitterApi, Task task);

    Entities transformEntitiesForTweet(Tweet tweetFromTwitterApi, Task task);
}
