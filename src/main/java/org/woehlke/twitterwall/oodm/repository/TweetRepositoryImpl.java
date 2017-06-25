package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindTweetByIdTwitterException;

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
            String name = "Tweet.findByIdTwitter";
            TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
            query.setParameter("idTwitter", idTwitter);
            Tweet result = query.getSingleResult();
            log.info("found: " + idTwitter);
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + idTwitter);
            throw new FindTweetByIdTwitterException(e, idTwitter);
        }
    }

    @Override
    public Tweet persist(Tweet myTweet) {
        entityManager.persist(myTweet);
        entityManager.flush();
        myTweet = findByIdTwitter(myTweet.getIdTwitter());
        log.info("persisted: " + myTweet.toString());
        return myTweet;
    }

    @Override
    public Tweet update(Tweet myTweet) {
        myTweet = entityManager.merge(myTweet);
        entityManager.flush();
        myTweet = findByIdTwitter(myTweet.getIdTwitter());
        log.info("updated: " + myTweet.toString());
        return myTweet;
    }

    @Override
    public List<Tweet> getLatestTweets() {
        String name = "Tweet.getLatestTweets";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setMaxResults(frontendMaxResults);
        return query.getResultList();
    }

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        String name = "Tweet.getTweetsForHashTag";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setParameter("hashtagText", hashtagText);
        return query.getResultList();
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        String name = "Tweet.countTweetsForHashTag";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setParameter("hashtagText", hashtagText);
        return this.getTweetsForHashTag(hashtagText).size();
    }

    @Override
    public long count() {
        String name = "Tweet.count";
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        long count = query.getSingleResult();
        return count;
    }

    @Override
    public List<Tweet> getTweetsForUser(User user) {
        String name = "Tweet.getTweetsForUser";
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Long> getAllTwitterIds() {
        String name = "Tweet.getAllTwitterIds";
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        return query.getResultList();
    }
}
