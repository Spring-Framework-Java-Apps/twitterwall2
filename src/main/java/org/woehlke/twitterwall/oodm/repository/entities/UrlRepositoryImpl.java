package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class UrlRepositoryImpl implements UrlRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Url persist(Url url) {
        entityManager.persist(url);
        return url;
    }

    @Override
    public Url update(Url url) {
        return entityManager.merge(url);
    }
}
