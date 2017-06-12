package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;

import javax.persistence.*;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class MyTwitterProfileRepositoryImpl implements MyTwitterProfileRepository {

    private static final Logger log = LoggerFactory.getLogger(MyTwitterProfileRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyTwitterProfile findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from MyTwitterProfile as t where t.idTwitter=:idTwitter";
            TypedQuery<MyTwitterProfile> query = entityManager.createQuery(SQL,MyTwitterProfile.class);
            query.setParameter("idTwitter",idTwitter);
            MyTwitterProfile result = query.getSingleResult();
            log.debug("found: "+result.getIdTwitter());
            return result;
        } catch (NoResultException e){
            log.debug("not found: "+idTwitter);
            return null;
        }
    }

    @Override
    public MyTwitterProfile persist(MyTwitterProfile myTwitterProfile) {
        entityManager.persist(myTwitterProfile);
        //entityManager.flush();
        myTwitterProfile=findByIdTwitter(myTwitterProfile.getIdTwitter());
        log.debug("persisted: "+myTwitterProfile.getIdTwitter());
        return myTwitterProfile;
    }

    @Override
    public MyTwitterProfile update(MyTwitterProfile myTwitterProfile) {
        myTwitterProfile=entityManager.merge(myTwitterProfile);
        //entityManager.flush();
        myTwitterProfile=findByIdTwitter(myTwitterProfile.getIdTwitter());
        log.debug("updated: "+myTwitterProfile.getIdTwitter());
        return myTwitterProfile;
    }
}
