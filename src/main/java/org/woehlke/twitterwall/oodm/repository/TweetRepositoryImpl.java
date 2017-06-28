package org.woehlke.twitterwall.oodm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class TweetRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<Tweet> implements TweetRepository {

    private static final Logger log = LoggerFactory.getLogger(TweetRepositoryImpl.class);
    
    @Value("${twitterwall.frontend.maxResults}")
    private int frontendMaxResults;

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
