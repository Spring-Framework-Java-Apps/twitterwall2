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
            List<Tweet> fetchedTweets = getTwitterProxy().searchOperations().search(searchQuery, pageSize).getTweets();
            if(fetchedTweets == null){
                return new ArrayList<>();
            } else {
                return fetchedTweets;
            }
    }

    @Override
    public Tweet findOneTweetById(long id) {
        String msg = "findOneTweetById: "+id;
        log.debug(msg);
            Tweet result = getTwitterProxy().timelineOperations().getStatus(id);
            log.debug(msg+result.getId());
            return result;
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = "findOneTweetById: "+userProfileTwitterId;
        log.debug(msg);
            TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
            log.debug(msg+result.getId());
            log.debug(msg+result.getScreenName());
            log.debug(msg+result.getName());
            return result;
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = "getUserProfileForScreenName: "+screenName;
        log.debug(msg);
            TwitterProfile result = getTwitterProxy().userOperations().getUserProfile(screenName);log.debug(msg+result.getId());
            log.debug(msg+result.getScreenName());
            log.debug(msg+result.getName());
            return result;
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName) {
        String msg = "findUsersFromDefinedList: "+fetchUserListName+" ";
        log.debug(msg);
            List<TwitterProfile> result = getTwitterProxy().listOperations().getListMembers(screenName,fetchUserListName);
            log.debug(msg+result.size());
            return result;
    }
}
