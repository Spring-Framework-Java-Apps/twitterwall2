package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.exceptions.common.OodmException;
import org.woehlke.twitterwall.oodm.repository.TweetRepository;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.process.tasks.ScheduledTasksFacade.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

/**
 * Created by tw on 10.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet persist(Tweet myTweet) {
        return tweetRepository.persist(myTweet);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public Tweet update(Tweet myTweet) {
        return tweetRepository.update(myTweet);
    }

    @Override
    public List<Tweet> getLatestTweets() {
        return tweetRepository.getLatestTweets();
    }

    private final static String MSG = "hashtagText is not valid";

    @Override
    public List<Tweet> getTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new OodmException("getTweetsForHashTag: "+MSG);
        }
        return tweetRepository.getTweetsForHashTag(hashtagText);
    }

    @Override
    public long countTweetsForHashTag(String hashtagText) {
        if(!HashTag.isValidText(hashtagText)){
            throw new OodmException("countTweetsForHashTag: "+MSG);
        }
        return tweetRepository.countTweetsForHashTag(hashtagText);
    }

    @Override
    public long count() {
        return tweetRepository.count();
    }

    @Override
    public List<Tweet> getTestTweetsForTweetTest() {
        List<Tweet> list = new ArrayList<>();
        for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
            Tweet tweet = this.findByIdTwitter(idTwitter);
            list.add(tweet);
        }
        return list;
    }

    @Override
    public List<Tweet> getTweetsForUser(User user) {
        if(user == null){
            throw new OodmException("getTweetsForUser: user is null");
        }
        return tweetRepository.getTweetsForUser(user);
    }

    @Override
    public List<Long> getAllTwitterIds() {
        return tweetRepository.getAllTwitterIds();
    }

    @Override
    public Tweet findByIdTwitter(long idTwitter) {
        return tweetRepository.findByIdTwitter(idTwitter);
    }
}
