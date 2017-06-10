package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.repository.MyTweetRepository;

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
}
