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
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TweetServiceImpl extends DomainServiceWithTaskImpl<Tweet> implements TweetService {

    private static final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, TaskRepository taskRepository) {
        super(tweetRepository,taskRepository);
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Page<Tweet> findTweetsForHashTag(HashTag hashtag, Pageable pageRequest) {
        return tweetRepository.findByHashTag(hashtag.getText(),pageRequest);
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

}
