package org.woehlke.twitterwall.backend.service.remote.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.configuration.properties.TwitterProperties;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.model.parts.User2UserList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tw on 19.06.17.
 */
@Component
public class TwitterApiServiceImpl implements TwitterApiService {

    @Override
    public List<Tweet> findTweetsForSearchQuery() {
        String msg = MSG+"findTweetsForSearchQuery: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().searchOperations().search(twitterProperties.getSearchQuery(), twitterProperties.getPageSize()).getTweets();
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public Tweet findOneTweetById(long tweetTwitterId) {
        String msg = MSG + "findOneTweetById: " + tweetTwitterId;
        log.debug(msg);
        Tweet result;
        while(true) {
            try {
                result = getTwitterProxy().timelineOperations().getStatus(tweetTwitterId);
                log.debug(msg + " Id: " + result.getId());
                msg += " result: ";
                log.debug(msg + result);
                waitForApi();
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public List<Tweet> getHomeTimeline() {
        String msg = MSG+"getHomeTimeline: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().timelineOperations().getHomeTimeline(twitterProperties.getPageSize());
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Tweet> getUserTimeline() {
        String msg = MSG+"getUserTimeline: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().timelineOperations().getUserTimeline(twitterProperties.getPageSize());
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    //TODO: Why?
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Tweet> getMentions() {
        String msg = MSG+"getMentions: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().timelineOperations().getMentions(twitterProperties.getPageSize());
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Tweet> getFavorites() {
        String msg = MSG+"getMentions: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().timelineOperations().getFavorites(twitterProperties.getPageSize());
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Tweet> getRetweetsOfMe(){
        String msg = MSG+"getMentions: ";
        log.debug(msg);
        List<Tweet> fetchedTweets;
        while(true) {
            try {
                fetchedTweets = getTwitterProxy().timelineOperations().getRetweetsOfMe();
                msg += " result: ";
                if (fetchedTweets.size() == 0) {
                    log.error(msg + " result.size: 0");
                    return new ArrayList<>();
                } else {
                    log.debug(msg + " result.size: " + fetchedTweets.size());
                    return fetchedTweets;
                }
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public User2UserList getLists(){
        String msg = MSG+"getMentions: ";
        log.debug(msg);
        long idTwitterOfListOwningUser  = getTwitterProxy().userOperations().getProfileId();
        String exceptionMsg1 = "getTwitterProxy().listOperations().getLists("+idTwitterOfListOwningUser+") ";
        boolean doTheJob = true;
        while(doTheJob) {
            try {
                User2UserList result = getUserListForUser(idTwitterOfListOwningUser);
                doTheJob = false;
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : " + exceptionMsg1);
                waitLongerForApi();
            } catch (Exception e) {
                log.warn(msg + "Exception at: " + exceptionMsg1 + e.getMessage());
            }
        }
        return null;
    }


    @Override
    public User2UserList getUserListForUser(long idTwitterOfListOwningUser){
        String msg = MSG+"getMentions: ";
        String exceptionMsg1 = "getTwitterProxy().listOperations().getLists("+idTwitterOfListOwningUser+") ";
        String exceptionMsg2 = "getTwitterProxy().listOperations().getSubscriptions("+idTwitterOfListOwningUser+") ";
        String exceptionMsg3 = "getTwitterProxy().listOperations().getMemberships("+idTwitterOfListOwningUser+") ";
        log.debug(msg);
        Set<UserList> ownLists = new HashSet<>();
        Set<UserList> userListSubcriptions = new HashSet<>();
        Set<UserList> userListMemberships = new HashSet<>();
        boolean doTheJob = true;
        while(doTheJob) {
            try {
                List<UserList> fetchedLists= getTwitterProxy().listOperations().getLists(idTwitterOfListOwningUser);
                msg += " result: ";
                ownLists.addAll(fetchedLists);
                log.debug(msg + " result.size: " + fetchedLists.size());
                waitForApi();
                doTheJob = false;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : "+exceptionMsg1);
                waitLongerForApi();
            } catch (Exception e) {
                log.warn(msg + "Exception at: "+exceptionMsg1+ e.getMessage());
            }
        }
        doTheJob = true;
        while(doTheJob) {
            try {
                List<UserList> fetchedLists= getTwitterProxy().listOperations().getSubscriptions(idTwitterOfListOwningUser);
                msg += " result: ";
                userListSubcriptions.addAll(fetchedLists);
                log.debug(msg + " result.size: " + fetchedLists.size());
                waitForApi();
                doTheJob = false;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : "+exceptionMsg2);
                waitLongerForApi();
            } catch (Exception e) {
                log.warn(msg + "Exception at: "+exceptionMsg2+ e.getMessage());
            }
        }
        doTheJob = true;
        while(doTheJob) {
            try {
                List<UserList> fetchedOwnLists = getTwitterProxy().listOperations().getMemberships(idTwitterOfListOwningUser);
                msg += " result: ";
                userListMemberships.addAll(fetchedOwnLists);
                log.debug(msg + " result.size: " + fetchedOwnLists.size());
                waitForApi();
                doTheJob = false;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : "+exceptionMsg3);
                waitLongerForApi();
            } catch (Exception e) {
                log.warn(msg + "Exception at: "+exceptionMsg3+ e.getMessage());
            }
        }
        User2UserList result = new User2UserList();
        result.setIdTwitterOfListOwningUser(idTwitterOfListOwningUser);
        result.setOwnLists(ownLists);
        result.setUserListMemberships(userListSubcriptions);
        result.setUserListMemberships(userListMemberships);
        return result;
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        String msg = MSG+"getUserProfileForTwitterId: "+userProfileTwitterId+" : ";
        log.debug(msg);
        TwitterProfile result;
        while(true) {
            try {
                result = getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
                msg += " result: ";
                log.debug(msg + " Id:         " + result.getId());
                log.debug(msg + " ScreenName: " + result.getScreenName());
                log.debug(msg + " Name:       " + result.getName());
                waitForApi();
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (ResourceNotFoundException e) {
                log.warn(msg + "  User not found : " + userProfileTwitterId);
                return null;
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public TwitterProfile getUserProfileForScreenName(String screenName) {
        String msg = MSG+"getUserProfileForScreenName: "+screenName;
        log.debug(msg);
        TwitterProfile result;
        while(true) {
            try {
                result = getTwitterProxy().userOperations().getUserProfile(screenName);
                msg += " result: ";
                log.debug(msg + " ScreenName: " + result.getScreenName());
                log.debug(msg + " Name:       " + result.getName());
                waitForApi();
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (ResourceNotFoundException e) {
                log.warn(msg + "  User not found : " + screenName);
                return null;
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public List<TwitterProfile> findUsersFromDefinedList(String screenNameOfTheListOwner,String listSlug) {
        String msg = MSG+"findUsersFromDefinedList: listSlug = "+listSlug+" ";
        log.debug(msg);
        List<TwitterProfile> result;
        while(true) {
            try {
                result = getTwitterProxy().listOperations().getListMembers(screenNameOfTheListOwner, listSlug);
                log.debug(msg + " result.size: " + result.size());
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new ArrayList<>();
            }
        }
    }

    @Override
    public CursoredList<Long> getFollowerIds() {
        String msg = MSG+"getFollowerIds: ";
        log.debug(msg);
        CursoredList<Long> result;
        while(true) {
            try {
                result = getTwitterProxy().friendOperations().getFollowerIds();
                log.debug(msg + " result.size: " + result.size());
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new CursoredList<>(new ArrayList<>(), 0L, 0L);
            }
        }
    }

    @Override
    public CursoredList<Long> getFriendIds() {
        String msg = MSG+"findFriendss: ";
        log.debug(msg);
        CursoredList<Long> result;
        while(true) {
            try {
                result = getTwitterProxy().friendOperations().getFriendIds();
                log.debug(msg + " result.size: " + result.size());
                return result;
            } catch (RateLimitExceededException e) {
                log.warn(msg + "  Rate Limit Exceeded : ");
                waitLongerForApi();
            } catch (Exception e) {
                log.error(msg + e.getMessage());
                return new CursoredList<>(new ArrayList<>(), 0L, 0L);
            }
        }
    }

    private void waitForApi(){
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        log.debug("### waiting now for (ms): "+millisToWaitBetweenTwoApiCalls);
        try {
            Thread.sleep(millisToWaitBetweenTwoApiCalls);
        } catch (InterruptedException e) {
        }
    }

    private void waitLongerForApi(){
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        millisToWaitBetweenTwoApiCalls *= 100;
        log.debug("### waiting now for (ms): "+millisToWaitBetweenTwoApiCalls);
        try {
            Thread.sleep(millisToWaitBetweenTwoApiCalls);
        } catch (InterruptedException e) {
        }
    }

    @Inject
    private Environment environment;

    private static final Logger log = LoggerFactory.getLogger(TwitterApiServiceImpl.class);

    private final TwitterProperties twitterProperties;

    private final FrontendProperties frontendProperties;

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
    public TwitterApiServiceImpl(TwitterProperties twitterProperties, FrontendProperties frontendProperties) {
        this.twitterProperties = twitterProperties;
        this.frontendProperties = frontendProperties;
    }
}
