package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyMediaEntity;
import org.woehlke.twitterwall.oodm.entities.MyMentionEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyMentionEntityRepositoryImpl implements MyMentionEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyMentionEntity persist(MyMentionEntity myMentionEntity) {
        entityManager.persist(myMentionEntity);
        return findByIdTwitter(myMentionEntity.getIdTwitter());
    }

    @Override
    public MyMentionEntity update(MyMentionEntity myMentionEntity) {
        return entityManager.merge(myMentionEntity);
    }

    @Override
    public MyMentionEntity findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from MyMentionEntity as t where t.idTwitter=:idTwitter";
            TypedQuery<MyMentionEntity> query = entityManager.createQuery(SQL,MyMentionEntity.class);
            query.setParameter("idTwitter",idTwitter);
            MyMentionEntity result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
