package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.Task;

/**
 * Created by tw on 11.07.17.
 */
public interface StoreEntitiesProcess {

    Entities storeEntitiesProcessForTweet(Tweet tweet, Task task);

    Entities storeEntitiesProcessForUser(User user, Task task);

    Entities updateEntitiesForUserProcess(User user, Task task);

    Entities storeEntitiesProcess(Entities entities, Task task);
}
