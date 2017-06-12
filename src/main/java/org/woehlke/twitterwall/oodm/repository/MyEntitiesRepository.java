package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyEntities;

/**
 * Created by tw on 12.06.17.
 */
public interface MyEntitiesRepository {

    MyEntities persist(MyEntities myEntities);

    MyEntities update(MyEntities myEntities);
}
