package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.exceptions.FindMentionByIdTwitter;
import org.woehlke.twitterwall.oodm.exceptions.FindMentionByScreenNameAndName;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MentionRepositoryImpl implements MentionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mention persist(Mention myMentionEntity) {
        entityManager.persist(myMentionEntity);
        return findByIdTwitter(myMentionEntity.getIdTwitter());
    }

    @Override
    public Mention update(Mention myMentionEntity) {
        return entityManager.merge(myMentionEntity);
    }

    @Override
    public Mention findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from Mention as t where t.idTwitter=:idTwitter";
            TypedQuery<Mention> query = entityManager.createQuery(SQL,Mention.class);
            query.setParameter("idTwitter",idTwitter);
            Mention result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            throw new FindMentionByIdTwitter(e,idTwitter);
        }
    }

    @Override
    public Mention findByScreenNameAndName(String screenName,String name) {
        try {
            String SQL = "select t from Mention as t where t.screenName=:screenName and t.name=:name";
            TypedQuery<Mention> query = entityManager.createQuery(SQL,Mention.class);
            query.setParameter("screenName",screenName);
            query.setParameter("name",name);
            Mention result = query.getSingleResult();
            return result;
        } catch (NoResultException e){
            throw new FindMentionByScreenNameAndName(e,screenName,name);
        }
    }
}
