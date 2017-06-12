package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyMediaEntity;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyMediaEntityRepositoryImpl implements MyMediaEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyMediaEntity persist(MyMediaEntity myMediaEntity) {
        entityManager.persist(myMediaEntity);
        return findByIdTwitter(myMediaEntity.getIdTwitter());
    }

    @Override
    public MyMediaEntity update(MyMediaEntity myMediaEntity) {
        return entityManager.merge(myMediaEntity);
    }

    @Override
    public MyMediaEntity findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from MyMediaEntity as t where t.idTwitter=:idTwitter";
            TypedQuery<MyMediaEntity> query = entityManager.createQuery(SQL,MyMediaEntity.class);
            query.setParameter("idTwitter",idTwitter);
            MyMediaEntity result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
