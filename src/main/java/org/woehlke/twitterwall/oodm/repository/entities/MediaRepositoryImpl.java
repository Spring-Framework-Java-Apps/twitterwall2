package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Media;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MediaRepositoryImpl implements MediaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Media persist(Media myMediaEntity) {
        entityManager.persist(myMediaEntity);
        return findByIdTwitter(myMediaEntity.getIdTwitter());
    }

    @Override
    public Media update(Media myMediaEntity) {
        return entityManager.merge(myMediaEntity);
    }

    @Override
    public Media findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from Media as t where t.idTwitter=:idTwitter";
            TypedQuery<Media> query = entityManager.createQuery(SQL,Media.class);
            query.setParameter("idTwitter",idTwitter);
            Media result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
