package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;

/**
 * Created by tw on 11.07.17.
 */
public interface StoreEntitiesProcess {

    Entities storeEntitiesProcess(Entities entities,Task task, String url);

    //Entities transform(org.springframework.social.twitter.api.Entities twitterEntities);

    //Entities getEntitiesFromUser(User user);
}
