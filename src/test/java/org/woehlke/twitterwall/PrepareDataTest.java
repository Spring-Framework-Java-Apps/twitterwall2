package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.frontend.controller.pages.TestControllerTest;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController.ID_TWITTER_TO_FETCH_FOR_PROFILE_CONTROLLER_TEST;
import static org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

/**
 * Created by tw on 13.07.17.
 */
public abstract class PrepareDataTest implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TestControllerTest.class);

    protected void getTestDataTweets(String msg){
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_TWEETS);
        List<Tweet> latest =  new ArrayList<>();
        try {
            log.info(msg + "--------------------------------------------------------------------");
            int loopId = 0;
            for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
                try {
                    org.springframework.social.twitter.api.Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                    loopId++;
                    log.info(msg + loopId);
                    org.woehlke.twitterwall.oodm.entities.Tweet persTweet = this.storeOneTweet.storeOneTweet(tweet, task);
                    log.info(msg + "--------------------------------------------------------------------");
                    log.info(msg + persTweet.toString());
                    log.info(msg + "--------------------------------------------------------------------");
                    latest.add(persTweet);
                } catch (EmptyResultDataAccessException e) {
                    log.warn(msg + e.getMessage());
                } catch (NoResultException e) {
                    log.warn(msg + e.getMessage());
                }
            }
        } catch (RateLimitExceededException e) {
            log.info(msg + e.getMessage());
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
        } finally {
            log.info(msg + "--------------------------------------------------------------------");
        }
        for(Tweet tweet:latest){
            log.debug(msg + tweet.toString());
        }
        taskService.done(task);
    }

    protected void getTestDataUser(String msg){
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_USER);
        List<org.woehlke.twitterwall.oodm.entities.User> user =  new ArrayList<>();
        try {
            int loopId = 0;
            for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_PROFILE_CONTROLLER_TEST) {
                try {
                    TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(idTwitter);
                    loopId++;
                    log.info(msg + loopId);
                    org.woehlke.twitterwall.oodm.entities.User persUser = this.storeUserProfile.storeUserProfile(twitterProfile,task);
                    user.add(persUser);
                } catch (EmptyResultDataAccessException e) {
                    log.warn(msg + e.getMessage());
                } catch (NoResultException e) {
                    log.warn(msg + e.getMessage());
                }
            }
            try {
                TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(imprintScreenName);
                loopId++;
                log.info(msg + loopId);
                org.woehlke.twitterwall.oodm.entities.User persUser = this.storeUserProfile.storeUserProfile(twitterProfile,task);
                user.add(persUser);
            } catch (EmptyResultDataAccessException e) {
                log.warn(msg + e.getMessage());
            } catch (NoResultException e) {
                log.warn(msg + e.getMessage());
            }
        } catch (RateLimitExceededException e) {
            log.info(msg + e.getMessage());
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
        }
        for(org.woehlke.twitterwall.oodm.entities.User oneUser:user){
            log.debug(msg + oneUser.toString());
        }
        taskService.done(task);
    }

    protected void setupAfterPropertiesSetWithTesting(TaskService taskService, TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, UserService userService, String menuAppName, String searchterm, String infoWebpage, String theme, boolean contextTest , String imprintScreenName, String idGoogleAnalytics) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.userService = userService;
        this.taskService=taskService;
        this.setupAfterPropertiesSet(menuAppName, searchterm, infoWebpage, theme, contextTest, imprintScreenName, idGoogleAnalytics);
    }

    protected void setupAfterPropertiesSet(String menuAppName, String searchterm, String infoWebpage, String theme, boolean contextTest ,String imprintScreenName,String idGoogleAnalytics) {
        this.menuAppName = menuAppName;
        this.searchterm = searchterm;
        this.infoWebpage = infoWebpage;
        this.theme = theme;
        this.contextTest = contextTest;
        this.imprintScreenName = imprintScreenName;
        this.idGoogleAnalytics = idGoogleAnalytics;
        logEnv();
    }

    private TwitterApiService twitterApiService;

    private StoreOneTweet storeOneTweet;

    private StoreUserProfile storeUserProfile;

    private UserService userService;

    private TaskService taskService;

    private String menuAppName;

    private String searchterm;

    private String infoWebpage;

    private String theme;

    private boolean contextTest;

    private String imprintScreenName;

    private String idGoogleAnalytics;

    protected void logEnv(){
        log.info("--------------------------------------------------------------------");
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.context.test = "+contextTest);
        log.info("twitterwall.frontend.imprint.screenName = "+imprintScreenName);
        log.info("twitterwall.frontend.idGoogleAnalytics = "+idGoogleAnalytics);
        log.info("--------------------------------------------------------------------");
    }
}
