package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.repository.entities.HashTagRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class HashTagRepositoryImpl implements HashTagRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public HashTag persist(HashTag hashTag) {
        entityManager.persist(hashTag);
        return hashTag;
    }

    @Override
    public HashTag update(HashTag hashTag) {
        return entityManager.merge(hashTag);
    }
}
