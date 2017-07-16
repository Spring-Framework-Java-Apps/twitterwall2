package org.woehlke.twitterwall.scheduled.service.persist;

import org.woehlke.twitterwall.oodm.entities.parts.Entities;
import org.woehlke.twitterwall.oodm.entities.Task;

/**
 * Created by tw on 11.07.17.
 */
public interface StoreEntitiesProcess {

    Entities storeEntitiesProcess(Entities entities,Task task);

}
