package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

/**
 * Created by tw on 12.06.17.
 */
public interface EntitiesService extends OodmService {

    Entities store(Entities myEntities);

    Entities findByIdTwitterFromTweet(long idTwitterFromTweet);

    Entities update(Entities myEntitiesPers);

}
