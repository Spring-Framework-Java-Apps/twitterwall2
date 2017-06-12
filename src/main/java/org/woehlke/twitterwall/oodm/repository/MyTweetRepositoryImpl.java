package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyTweet;

import javax.persistence.*;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class MyTweetRepositoryImpl implements MyTweetRepository {

    private static final Logger log = LoggerFactory.getLogger(MyTweetRepositoryImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyTweet findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from MyTweet as t where t.idTwitter=:idTwitter";
            TypedQuery<MyTweet> query = entityManager.createQuery(SQL,MyTweet.class);
            query.setParameter("idTwitter",idTwitter);
            MyTweet result = query.getSingleResult();
            log.debug("found: "+result.getIdTwitter());
            return result;
        } catch (NoResultException e){
            log.debug("not found: "+idTwitter);
            return null;
        }
    }

    @Override
    public MyTweet persist(MyTweet myTweet) {
        entityManager.persist(myTweet);
        entityManager.flush();
        myTweet=findByIdTwitter(myTweet.getIdTwitter());
        log.debug("persisted: "+myTweet.getIdTwitter());
        return myTweet;
    }

    @Override
    public MyTweet update(MyTweet myTweet) {
        myTweet = entityManager.merge(myTweet);
        entityManager.flush();
        myTweet=findByIdTwitter(myTweet.getIdTwitter());
        log.debug("updated: "+myTweet.getIdTwitter());
        return myTweet;
    }
}
