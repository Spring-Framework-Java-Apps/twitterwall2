package org.woehlke.twitterwall.scheduled.service.backend.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterApiServiceImpl implements TwitterApiService {


    @Override
    public List<Tweet> findTweetsForSearchQuery() {
        String msg = MSG+"findTweetsForSearchQuery: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        try {
            fetchedTweets = getTwitterProxy().searchOperations().search(twitterProperties.getSearchQuery(), twitterProperties.getPageSize()).getTweets();
        } catch (Exception e) {
            fetchedTweets = new ArrayList<>();
            log.error(msg + e.getMessage());
            e.printStackTrace();
        }
        msg += " result: ";
        if(fetchedTweets.size()==0){
            log.error(msg+" result.size: 0");
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
        Tweet result;
        try {
            result = getTwitterProxy().timelineOperations().getStatus(id);
            log.debug(msg+" Id: "+result.getId());
            msg += " result: ";
            log.debug(msg+result);
        } catch (Exception e){
            result = null;
            log.error(msg + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TwitterProfile> getUserProfilesForTwitterIds(long... userProfileTwitterIds) {
        String msg = MSG+"getUserProfileForTwitterId: "+userProfileTwitterIds+" : ";
        log.debug(msg);
        List<TwitterProfile> result;
        try {
            result = getTwitterProxy().userOperations().getUsers(userProfileTwitterIds);
            msg += " result: ";
            log.debug(msg+" size: "+result.size());
        } catch (Exception e){
            result = null;
            log.error(msg + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = MSG+"getUserProfileForTwitterId: "+userProfileTwitterId+" : ";
        log.debug(msg);
        TwitterProfile result;
        try {
            result = getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
            msg += " result: ";
            log.debug(msg+" Id:         "+result.getId());
            log.debug(msg+" ScreenName: "+result.getScreenName());
            log.debug(msg+" Name:       "+result.getName());
        } catch (Exception e) {
            result = null;
            log.error(msg + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = MSG+"getUserProfileForScreenName: "+screenName;
        log.debug(msg);
        TwitterProfile result;
        try {
            result= getTwitterProxy().userOperations().getUserProfile(screenName);
            msg += " result: ";
            log.debug(msg+" ScreenName: "+result.getScreenName());
            log.debug(msg+" Name:       "+result.getName());
        } catch (Exception e) {
            result = null;
            log.debug(msg + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName) {
        String msg = MSG+"findUsersFromDefinedList: "+fetchUserListName+" ";
        log.debug(msg);
        List<TwitterProfile> result;
        try {
            result = getTwitterProxy().listOperations().getListMembers(screenName, fetchUserListName);
            log.debug(msg+" result.size: "+result.size());
        } catch (Exception e) {
            result = new ArrayList<>();
            log.debug(msg + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    @Inject
    private Environment environment;

    private static final Logger log = LoggerFactory.getLogger(TwitterApiServiceImpl.class);

    private final TwitterProperties twitterProperties;

    private Twitter getTwitterProxy() {

        String consumerKey =  environment.getProperty("TWITTER_CONSUMER_KEY");
        String consumerSecret =   environment.getProperty("TWITTER_CONSUMER_SECRET");
        String accessToken =  environment.getProperty("TWITTER_ACCESS_TOKEN");
        String accessTokenSecret =  environment.getProperty("TWITTER_ACCESS_TOKEN_SECRET");

        Twitter twitterTemplate = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        return twitterTemplate;
    }

    private String MSG = "Remote API Call ";

    @Autowired
    public TwitterApiServiceImpl(TwitterProperties twitterProperties) {
        this.twitterProperties = twitterProperties;
    }
}
