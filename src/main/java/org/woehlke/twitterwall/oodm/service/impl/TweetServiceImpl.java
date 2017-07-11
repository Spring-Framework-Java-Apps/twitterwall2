package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.*;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet create(Tweet myTweet) {
        return tweetRepository.persist(myTweet);
    }

    @Override
    public Tweet update(Tweet myTweet) {
        return tweetRepository.update(myTweet);
    }

    @Override
    public List<Tweet> getAll() {
        return tweetRepository.getAll(Tweet.class);
    }

    @Override
    public List<Tweet> getLatestTweets() {
        return tweetRepository.getLatestTweets();
    }

    private final static String MSG = "hashtagText is not valid";

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("getTweetsForHashTag: "+MSG);
        }
        return tweetRepository.getTweetsForHashTag(hashtagText);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new IllegalArgumentException("countTweetsForHashTag: "+MSG);
        }
        return tweetRepository.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetRepository.count(Tweet.class);
    }

    @Override
    public List<Tweet> getTweetsForUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("getTweetsForUser: user is null");
        }
        return tweetRepository.getTweetsForUser(user);
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return tweetRepository.getAllTwitterIds();
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter,Tweet.class);
    }

    @Override
    public Tweet store(Tweet tweet, Task task) {
        String name = "try to store: "+tweet.getIdTwitter()+" ";
        log.debug(name);
        Tweet result;
        try {
            Tweet tweetPersistent = tweetRepository.findByIdTwitter(tweet.getIdTwitter(),Tweet.class);
            tweet.setCreatedBy(tweetPersistent.getCreatedBy());
            tweet.setUpdatedBy(task);
            result = tweetRepository.update(tweet);
            log.debug(name+" updated "+result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            tweet.setCreatedBy(task);
            result = tweetRepository.persist(tweet);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }
}
