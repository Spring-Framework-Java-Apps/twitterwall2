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
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl implements TweetService {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, TaskRepository taskRepository) {
        this.tweetRepository = tweetRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Tweet create(Tweet myTweet, Task task) {
        myTweet.setCreatedBy(task);
        return tweetRepository.save(myTweet);
    }

    @Override
    public Tweet update(Tweet myTweet, Task task) {
        myTweet.setUpdatedBy(task);
        return tweetRepository.save(myTweet);
    }

    @Override
    public Page<Tweet> getAll(Pageable pageRequest) {
        return tweetRepository.findAll(pageRequest);
    }

    @Override
    public Page<Tweet> findTweetsForHashTag(HashTag hashtag, Pageable pageRequest) {
        return tweetRepository.findByHashTag(hashtag.getText(),pageRequest);
    }

    @Override
    public long count() {
        return tweetRepository.count();
    }

    @Override
    public Page<Tweet> findTweetsForUser(User user, Pageable pageRequest) {
        return tweetRepository.findByUser(user,pageRequest);
    }

    @Override
    public Page<Long> findAllTwitterIds(Pageable pageRequest) {
        return tweetRepository.findAllTwitterIds(pageRequest);
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Tweet store(Tweet tweet, Task task) {
        task.setTimeLastUpdate();
        task = this.taskRepository.save(task);
        String name = "try to store: "+tweet.getIdTwitter()+" ";
        log.debug(name);
        Tweet result;
        Tweet tweetPersistent = tweetRepository.findByIdTwitter(tweet.getIdTwitter());//tweetDao.findByIdTwitter(tweet.getIdTwitter(),Tweet.class);
        if(tweetPersistent!=null){
            tweet.setId(tweetPersistent.getId());
            tweet.setCreatedBy(tweetPersistent.getCreatedBy());
            tweet.setUpdatedBy(task);
            result = tweetRepository.save(tweet);
            log.debug(name+" updated "+result.toString());
            return result;
        } else {
            tweet.setCreatedBy(task);
            tweet.setUpdatedBy(task);
            result = tweetRepository.save(tweet);
            log.debug(name+" persisted "+result.toString());
            return result;
        }
    }
}
