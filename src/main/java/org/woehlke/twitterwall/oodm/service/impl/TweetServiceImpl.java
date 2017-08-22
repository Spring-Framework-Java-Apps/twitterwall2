package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.model.*;
import org.woehlke.twitterwall.oodm.model.transients.*;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.TweetRepository;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
    public Page<Tweet> findTweetsForMedia(Media media, Pageable pageRequestTweet) {
        return tweetRepository.findTweetsForMedia(media,pageRequestTweet);
    }

    @Override
    public Page<Tweet> findTweetsForMention(Mention mention, Pageable pageRequestTweet) {
        return tweetRepository.findTweetsForMention(mention,pageRequestTweet);
    }

    @Override
    public Page<Tweet> findTweetsForUrl(Url url, Pageable pageRequestTweet) {
        return tweetRepository.findTweetsForUrl(url, pageRequestTweet);
    }

    @Override
    public Page<Tweet> findTweetsForTickerSymbol(TickerSymbol tickerSymbol, Pageable pageRequestTweet) {
        return tweetRepository.findTweetsForTickerSymbol(tickerSymbol,pageRequestTweet);
    }

    @Override
    public Page<Tweet> findTweetsForUser(User user, Pageable pageRequest) {
        return tweetRepository.findByUser(user,pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllTweet2HashTag(Pageable pageRequest) {
        return tweetRepository.findAllTweet2HashTag(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllTweet2Media(Pageable pageRequest) {
        return tweetRepository.findAllTweet2Media(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllTweet2Mention(Pageable pageRequest) {
        return tweetRepository.findAllTweet2Mention(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllTweet2Url(Pageable pageRequest) {
        return tweetRepository.findAllTweet2Url(pageRequest);
    }

    @Override
    public Page<Object2Entity> findAllTweet2TickerSymbol(Pageable pageRequest) {
        return tweetRepository.findAllTweet2TickerSymbol(pageRequest);
    }

    @Override
    public Page<Tweet> getHomeTimeline(Pageable pageRequest) {
        return tweetRepository.getHomeTimeline(pageRequest);
    }

    @Override
    public Page<Tweet> getUserTimeline(Pageable pageRequest) {
        return tweetRepository.getUserTimeline(pageRequest);
    }

    @Override
    public Page<Tweet> getMentions(Pageable pageRequest) {
        return tweetRepository.getMentions(pageRequest);
    }

    @Override
    public Page<Tweet> getFavorites(Pageable pageRequest) {
        return tweetRepository.getFavorites(pageRequest);
    }

    @Override
    public Page<Tweet> getRetweetsOfMe(Pageable pageRequest) {
        return tweetRepository.getRetweetsOfMe(pageRequest);
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter);
    }

    @Override
    public Tweet findByUniqueId(Tweet domainExampleObject) {
        return tweetRepository.findByUniqueId(domainExampleObject);
    }
}
