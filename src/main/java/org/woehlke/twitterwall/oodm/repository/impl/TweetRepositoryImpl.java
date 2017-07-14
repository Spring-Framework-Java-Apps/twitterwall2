package org.woehlke.twitterwall.oodm.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;
import org.woehlke.twitterwall.oodm.repository.common.impl.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
@Repository
public class TweetRepositoryImpl extends DomainRepositoryWithIdTwitterImpl<Tweet> implements TweetRepository {

    private static final Logger log = LoggerFactory.getLogger(TweetRepositoryImpl.class);

    @Override
    public Page<Tweet> getLatestTweets(Pageable pageRequest) {
        String name = "Tweet.getLatestTweets";
        log.debug(name);
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<Tweet> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+total);
        return resultPage;
    }

    @Override
    public Page<Tweet> getTweetsForHashTag(String hashtagText,Pageable pageRequest) {
        String name = "Tweet.getTweetsForHashTag";
        log.debug(name);
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setParameter("hashtagText", hashtagText);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<Tweet> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+total);
        return resultPage;
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        String name = "Tweet.countTweetsForHashTag";
        log.debug(name);
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        query.setParameter("hashtagText", hashtagText);
        long result = query.getSingleResult();
        log.debug(name+"  "+result);
        return result;
    }

    @Override
    public Page<Tweet> getTweetsForUser(User user,Pageable pageRequest) {
        String name = "Tweet.getTweetsForUser";
        log.debug(name);
        TypedQuery<Tweet> query = entityManager.createNamedQuery(name, Tweet.class);
        query.setParameter("user", user);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<Tweet> result = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+total);
        return resultPage;
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        String name = "Tweet.getAllTwitterIds";
        log.debug(name);
        TypedQuery<Long> query = entityManager.createNamedQuery(name, Long.class);
        long total = query.getResultList().size();
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getOffset());
        List<Long> result  = query.getResultList();
        Page resultPage = new PageImpl(result,pageRequest,total);
        log.debug(name+"  "+total);
        return resultPage;
    }
}
