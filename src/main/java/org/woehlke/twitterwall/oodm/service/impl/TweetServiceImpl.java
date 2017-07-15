package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.dao.TweetDao;
import org.woehlke.twitterwall.oodm.repositories.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetDao tweetDao;

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetDao tweetDao, TweetRepository tweetRepository) {
        this.tweetDao = tweetDao;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet create(Tweet myTweet, Task task) {
        myTweet.setCreatedBy(task);
        return tweetDao.persist(myTweet);
    }

    @Override
    public Tweet update(Tweet myTweet, Task task) {
        myTweet.setUpdatedBy(task);
        return tweetDao.update(myTweet);
    }

    @Override
    public Page<Tweet> getAll(Pageable pageRequest) {
        return tweetDao.getAll(Tweet.class,pageRequest);
    }

    @Override
    public Page<Tweet> getLatestTweets(Pageable pageRequest) {
        return tweetDao.getLatestTweets(pageRequest);
    }

    private final static String MSG = "hashtagText is not valid";

    @Override
    public Page<Tweet> getTweetsForHashTag(String hashtagText,Pageable pageRequest) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("getTweetsForHashTag: "+MSG);
        }
        return tweetDao.getTweetsForHashTag(hashtagText,pageRequest);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("countTweetsForHashTag: "+MSG);
        }
        return tweetDao.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetDao.count(Tweet.class);
    }

    @Override
    public Page<Tweet> getTweetsForUser(User user,Pageable pageRequest) {
        if(user == null){
            throw new IllegalArgumentException("getTweetsForUser: user is null");
        }
        return tweetDao.getTweetsForUser(user,pageRequest);
    }

    @Override
    public Page<Long> getAllTwitterIds(Pageable pageRequest) {
        return tweetDao.getAllTwitterIds(pageRequest);
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetDao.findByIdTwitter(idTwitter,Tweet.class);
    }

    @Override
    public Tweet store(Tweet tweet, Task task) {
        String name = "try to store: "+tweet.getIdTwitter()+" ";
        log.debug(name);
        Tweet result;
        try {
            Tweet tweetPersistent = tweetDao.findByIdTwitter(tweet.getIdTwitter(),Tweet.class);
            tweet.setId(tweetPersistent.getId());
            tweet.setCreatedBy(tweetPersistent.getCreatedBy());
            tweet.setUpdatedBy(task);
            result = tweetDao.update(tweet);
            log.debug(name+" updated "+result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            tweet.setCreatedBy(task);
            result = tweetDao.persist(tweet);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }
}
