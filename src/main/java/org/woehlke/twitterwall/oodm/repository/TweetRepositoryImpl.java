package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.exceptions.FindTweetByIdTwitter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class TweetRepositoryImpl implements TweetRepository {

    private static final Logger log = LoggerFactory.getLogger(TweetRepositoryImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    @Value("${twitterwall.frontend.maxResults}")
    private int frontendMaxResults;

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        try {
            String SQL = "select t from Tweet as t where t.idTwitter=:idTwitter";
            TypedQuery<Tweet> query = entityManager.createQuery(SQL,Tweet.class);
            query.setParameter("idTwitter",idTwitter);
            Tweet result = query.getSingleResult();
            log.debug("found: "+result.getIdTwitter());
            return result;
        } catch (NoResultException e){
            log.debug("not found: "+idTwitter);
            throw new FindTweetByIdTwitter(e,idTwitter);
        }
    }

    @Override
    public Tweet persist(Tweet myTweet) {
        entityManager.persist(myTweet);
        entityManager.flush();
        myTweet=findByIdTwitter(myTweet.getIdTwitter());
        log.debug("persisted: "+myTweet.getIdTwitter());
        return myTweet;
    }

    @Override
    public Tweet update(Tweet myTweet) {
        myTweet = entityManager.merge(myTweet);
        entityManager.flush();
        myTweet=findByIdTwitter(myTweet.getIdTwitter());
        log.debug("updated: "+myTweet.getIdTwitter());
        return myTweet;
    }

    @Override
    public List<Tweet> getLatestTweets() {
        String SQL = "select t from Tweet as t order by t.createdAt DESC";
        TypedQuery<Tweet> query = entityManager.createQuery(SQL,Tweet.class);
        query.setMaxResults(frontendMaxResults);
        return query.getResultList();
    }

    @Override
    public boolean isNotYetStored(Tweet tweet) {
        return null == findByIdTwitter(tweet.getIdTwitter());
    }

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        String SQL = "select t from Tweet as t join t.entities.tags tag WHERE tag.text=:hashtagText";
        TypedQuery<Tweet> query = entityManager.createQuery(SQL,Tweet.class);
        query.setParameter("hashtagText",hashtagText);
        return query.getResultList();
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        String SQL = "select t from Tweet as t join t.entities.tags tag WHERE tag.text=:hashtagText";
        TypedQuery<Tweet> query = entityManager.createQuery(SQL,Tweet.class);
        query.setParameter("hashtagText",hashtagText);
        return this.getTweetsForHashTag(hashtagText).size();
    }
}
