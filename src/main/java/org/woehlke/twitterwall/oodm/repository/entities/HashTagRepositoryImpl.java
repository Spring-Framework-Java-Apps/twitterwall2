package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

    @Override
    public List<HashTagCounted> getHashTags() {
        String SQL = "select new org.woehlke.twitterwall.model.HashTagCounted(count(h.text),h.text) from HashTag as h group by h.text";
        TypedQuery<HashTagCounted> query = entityManager.createQuery(SQL,HashTagCounted.class);
        return query.getResultList();
    }

    @Override
    public HashTag findByText(String text) {
        try {
            String SQL = "select t from HashTag as t where t.text=:text";
            TypedQuery<HashTag> query = entityManager.createQuery(SQL,HashTag.class);
            query.setParameter("text",text);
            HashTag result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            return null;
        }
    }
}
