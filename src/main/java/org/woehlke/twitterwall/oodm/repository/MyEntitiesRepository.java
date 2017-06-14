package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.Entities;

/**
 * Created by tw on 12.06.17.
 */
public interface MyEntitiesRepository {

    Entities persist(Entities myEntities);

    Entities update(Entities myEntities);
}
