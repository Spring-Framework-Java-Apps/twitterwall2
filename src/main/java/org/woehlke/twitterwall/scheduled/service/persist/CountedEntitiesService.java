package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.application.CountedEntities;

/**
 * Created by tw on 09.07.17.
 */
public interface CountedEntitiesService {

    CountedEntities countAll();

    long countTweets();

    long countUsers();
}
