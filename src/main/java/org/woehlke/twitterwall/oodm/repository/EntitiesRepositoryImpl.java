package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class EntitiesRepositoryImpl implements EntitiesRepository {

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

    @Override
    public Entities findByIdTwitterFromTweet(long idTwitterFromTweet) {
        try {
            String SQL = "select e from Entities as e where e.idTwitterFromTweet=:idTwitterFromTweet";
            TypedQuery<Entities> query = entityManager.createQuery(SQL,Entities.class);
            query.setParameter("idTwitterFromTweet",idTwitterFromTweet);
            Entities result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
