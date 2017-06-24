package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.entities.Entities;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

/**
 * Created by tw on 12.06.17.
 */
public interface EntitiesRepository extends OodmRepository {

    Entities persist(Entities myEntities);

    Entities update(Entities myEntities);

    Entities findByIdTwitterFromTweet(long idTwitterFromTweet);
}
