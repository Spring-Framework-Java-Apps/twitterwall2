package org.woehlke.twitterwall.scheduled.service.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.service.TweetServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterApiServiceImpl implements TwitterApiService {

    private static final Logger log = LoggerFactory.getLogger(TwitterApiServiceImpl.class);

    @Value("${twitter.consumerKey}")
    private String twitterConsumerKey;

    @Value("${twitter.consumerSecret}")
    private String twitterConsumerSecret;

    @Value("${twitter.accessToken}")
    private String twitterAccessToken;

    @Value("${twitter.accessTokenSecret}")
    private String twitterAccessTokenSecret;

    @Value("${twitter.pageSize}")
    private int pageSize;

    @Value("${twitter.searchQuery}")
    private String searchQuery;

    private Twitter getTwitterProxy() {
        Twitter twitter = new TwitterTemplate(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterAccessToken,
                twitterAccessTokenSecret);
        return twitter;
    }

    @Override
    public List<Tweet> findTweetsForSearchQuery() {
        String msg = "findTweetsForSearchQuery: ";
        log.debug(msg);
        List<Tweet> tweets = new ArrayList<>();
        try {
            List<Tweet> fetchedTweets = getTwitterProxy().searchOperations().search(searchQuery, pageSize).getTweets();
            if(fetchedTweets == null){
                return tweets;
            } else {
                return fetchedTweets;
            }
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        } catch (RuntimeException e){
            throw new TwitterApiException(msg + " ", e);
        } catch (Exception e) {
            throw new TwitterApiException(msg + " ", e);
        }
    }

    @Override
    public Tweet findOneTweetById(long id) {
        String msg = "findOneTweetById: "+id;
        log.debug(msg);
        try {
            Tweet result = getTwitterProxy().timelineOperations().getStatus(id);
            log.debug(msg+result.getId());
            return result;
        } catch (ResourceAccessException e) {
            log.debug(msg+e.getMessage());
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = "findOneTweetById: "+userProfileTwitterId;
        log.debug(msg);
        try {
            TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
            log.debug(msg+result.getId());
            log.debug(msg+result.getScreenName());
            log.debug(msg+result.getName());
            return result;
        } catch (ResourceAccessException e) {
            log.debug(msg+e.getMessage());
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = "getUserProfileForScreenName: "+screenName;
        log.debug(msg);
        try {
            TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(screenName);log.debug(msg+result.getId());
            log.debug(msg+result.getScreenName());
            log.debug(msg+result.getName());
            return result;
        } catch (ResourceAccessException e) {
            log.debug(msg+e.getMessage());
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName) {
        String msg = "findUsersFromDefinedList: "+fetchUserListName+" ";
        log.debug(msg);
        try {
            List<TwitterProfile> result = getTwitterProxy().listOperations().getListMembers(screenName,fetchUserListName);
            log.debug(msg+result.size());
            return result;
        } catch (ResourceAccessException e) {
            log.debug(msg+e.getMessage());
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }
}
