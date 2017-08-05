package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;

/**
 * Created by tw on 09.07.17.
 */
public interface CountedEntitiesService {

    CountedEntities countAll();

    long countTweets();

    long countUsers();
}
