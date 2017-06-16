package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.Entities;

/**
 * Created by tw on 12.06.17.
 */
public interface EntitiesService {
    Entities store(Entities myEntities);

    Entities findByIdTwitterFromTweet(long idTwitterFromTweet);

    Entities update(Entities myEntitiesPers);

}