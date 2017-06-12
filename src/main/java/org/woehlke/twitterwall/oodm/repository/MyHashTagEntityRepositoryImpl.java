package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyHashTagEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyHashTagEntityRepositoryImpl implements MyHashTagEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public MyHashTagEntity persist(MyHashTagEntity myHashTagEntity) {
        entityManager.persist(myHashTagEntity);
        return myHashTagEntity;
    }

    @Override
    public MyHashTagEntity update(MyHashTagEntity myHashTagEntity) {
        return entityManager.merge(myHashTagEntity);
    }
}
