package org.woehlke.twitterwall.backend;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterApiServiceImpl implements TwitterApiService {

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
        try {
            return getTwitterProxy().timelineOperations().getStatus(id);
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = "findOneTweetById: "+userProfileTwitterId;
        try {
            return getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = "getUserProfileForScreenName: "+screenName;
        try {
            return getTwitterProxy().userOperations().getUserProfile(screenName);
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName) {
        String msg = "findUsersFromDefinedList: "+fetchUserListName;
        try {
            return getTwitterProxy().listOperations().getListMembers(screenName,fetchUserListName);
        } catch (ResourceAccessException e) {
            throw new TwitterApiException(msg + " check your Network Connection!", e);
        }
    }
}
