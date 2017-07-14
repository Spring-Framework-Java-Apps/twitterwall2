package org.woehlke.twitterwall.scheduled.service.backend.impl;

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
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

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

    private String MSG = "Remote API Call ";

    @Override
    public List<Tweet> findTweetsForSearchQuery() {
        String msg = MSG+"findTweetsForSearchQuery: ";
        log.debug(msg);
        List<Tweet> fetchedTweets = getTwitterProxy().searchOperations().search(searchQuery, pageSize).getTweets();
        msg += " result: ";
        if(fetchedTweets == null){
            log.debug(msg+" result.size: 0");
            return new ArrayList<>();
        } else {
            log.debug(msg+" result.size: "+fetchedTweets.size());
            return fetchedTweets;
        }
    }

    @Override
    public Tweet findOneTweetById(long id) {
        String msg = MSG+"findOneTweetById: "+id;
        log.debug(msg);
        Tweet result = getTwitterProxy().timelineOperations().getStatus(id);
        msg += " result: ";
        log.debug(msg+" Id: "+result.getId());
        return result;
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = MSG+"findOneTweetById: "+userProfileTwitterId;
        log.debug(msg);
        TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
        msg += " result: ";
        log.debug(msg+" Id:         "+result.getId());
        log.debug(msg+" ScreenName: "+result.getScreenName());
        log.debug(msg+" Name:       "+result.getName());
        return result;
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = MSG+"getUserProfileForScreenName: "+screenName;
        log.debug(msg);
        TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(screenName);
        log.debug(msg+result.getId());
        msg += " result: ";
        log.debug(msg+" ScreenName: "+result.getScreenName());
        log.debug(msg+" Name:       "+result.getName());
        return result;
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName) {
        String msg = MSG+"findUsersFromDefinedList: "+fetchUserListName+" ";
        log.debug(msg);
        List<TwitterProfile> result = getTwitterProxy().listOperations().getListMembers(screenName,fetchUserListName);
        log.debug(msg+" result.size: "+result.size());
        return result;
    }
}
