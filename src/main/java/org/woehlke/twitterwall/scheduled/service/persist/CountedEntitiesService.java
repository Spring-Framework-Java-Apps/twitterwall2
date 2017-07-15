package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.parts.AbstractTwitterObject;

/**
 * Created by tw on 09.07.17.
 */
public interface CountedEntitiesService {

    AbstractTwitterObject.CountedEntities countAll();

    long countTweets();

    long countUsers();
}
