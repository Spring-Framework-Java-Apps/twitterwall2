package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.repository.MyEntitiesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyEntitiesRepositoryImpl implements MyEntitiesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Entities persist(Entities myEntities) {
        entityManager.persist(myEntities);
        return myEntities;
    }

    @Override
    public Entities update(Entities myEntities) {
        return entityManager.merge(myEntities);
    }
}
