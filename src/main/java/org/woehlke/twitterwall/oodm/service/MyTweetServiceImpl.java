package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyTweet;
import org.woehlke.twitterwall.oodm.repository.MyTweetRepository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyTweetServiceImpl implements MyTweetService {

    private final MyTweetRepository myTweetRepository;

    @Autowired
    public MyTweetServiceImpl(MyTweetRepository myTweetRepository) {
        this.myTweetRepository = myTweetRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyTweet persist(MyTweet myTweet) {
        return myTweetRepository.persist(myTweet);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyTweet update(MyTweet myTweet) {
        return myTweetRepository.update(myTweet);
    }

    @Override
    public List<MyTweet> getLatestTweets() {
        return myTweetRepository.getLatestTweets();
    }

    @Override
    public MyTweet findByIdTwitter(long idTwitter) {
        return myTweetRepository.findByIdTwitter(idTwitter);
    }
}
