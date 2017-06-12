package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyUrlEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyUrlEntityRepositoryImpl implements MyUrlEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyUrlEntity persist(MyUrlEntity myUrlEntity) {
        entityManager.persist(myUrlEntity);
        return myUrlEntity;
    }

    @Override
    public MyUrlEntity update(MyUrlEntity myUrlEntity) {
        return entityManager.merge(myUrlEntity);
    }
}
