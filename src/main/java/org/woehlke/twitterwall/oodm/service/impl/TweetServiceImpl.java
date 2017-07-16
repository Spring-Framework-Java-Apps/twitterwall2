package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Task;
//import org.woehlke.twitterwall.oodm.dao.TweetDao;
import org.woehlke.twitterwall.oodm.repositories.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    //private final TweetDao tweetDao;

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        //this.tweetDao = tweetDao;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet create(Tweet myTweet, Task task) {
        myTweet.setCreatedBy(task);
        return tweetRepository.save(myTweet);
        //return tweetDao.persist(myTweet);
    }

    @Override
    public Tweet update(Tweet myTweet, Task task) {
        myTweet.setUpdatedBy(task);
        return tweetRepository.save(myTweet);
        //return tweetDao.update(myTweet);
    }

    @Override
    public Page<Tweet> getAll(Pageable pageRequest) {
        return tweetRepository.findAll(pageRequest);
        //return tweetDao.getAll(Tweet.class,pageRequest);
    }
/*
    @Override
    public Page<Tweet> findLatestTweets(Pageable pageRequest) {
        return tweetRepository.findLatestTweets(pageRequest);
        //return tweetDao.findLatestTweets(pageRequest);
    }
    */

    private final static String MSG = "hashtagText is not valid";

    @Override
    public Page<Tweet> findTweetsForHashTag(String hashtagText, Pageable pageRequest) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("findTweetsForHashTag: "+MSG);
        }
        return tweetRepository.findTweetsForHashTag(hashtagText,pageRequest);
        //return tweetDao.findTweetsForHashTag(hashtagText,pageRequest);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("countTweetsForHashTag: "+MSG);
        }
        return tweetRepository.countTweetsForHashTag(hashtagText);
        //return tweetDao.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetRepository.count();
        //return tweetDao.count(Tweet.class);
    }

    @Override
    public Page<Tweet> findTweetsForUser(User user, Pageable pageRequest) {
        return tweetRepository.findByUser(user,pageRequest);
        //return tweetDao.findByUser(user,pageRequest);
    }

    @Override
    public Page<Long> findAllTwitterIds(Pageable pageRequest) {
        return tweetRepository.findAllTwitterIds(pageRequest);
        //return tweetDao.findAllTwitterIds(pageRequest);
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter);
        //return tweetDao.findByIdTwitter(idTwitter,Tweet.class);
    }

    @Override
    public Tweet store(Tweet tweet, Task task) {
        String name = "try to store: "+tweet.getIdTwitter()+" ";
        log.debug(name);
        Tweet result;
        Tweet tweetPersistent = tweetRepository.findByIdTwitter(tweet.getIdTwitter());//tweetDao.findByIdTwitter(tweet.getIdTwitter(),Tweet.class);
        if(tweetPersistent!=null){
            tweet.setId(tweetPersistent.getId());
            tweet.setCreatedBy(tweetPersistent.getCreatedBy());
            tweet.setUpdatedBy(task);
            //result = tweetDao.update(tweet);
            result = tweetRepository.save(tweet);
            log.debug(name+" updated "+result.toString());
            return result;
        } else {
            tweet.setCreatedBy(task);
            //result = tweetDao.persist(tweet);
            result = tweetRepository.save(tweet);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }
}
