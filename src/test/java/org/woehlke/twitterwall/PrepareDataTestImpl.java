package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 13.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class PrepareDataTestImpl implements PrepareDataTest {

    private static final Logger log = LoggerFactory.getLogger(PrepareDataTestImpl.class);

    @Autowired
    public PrepareDataTestImpl(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, UserService userService, TaskService taskService, SchedulerProperties schedulerProperties, FrontendProperties frontendProperties, TwitterProperties twitterProperties, CountedEntitiesService countedEntitiesService) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.userService = userService;
        this.taskService = taskService;
        this.schedulerProperties = schedulerProperties;
        this.frontendProperties = frontendProperties;
        this.twitterProperties = twitterProperties;
        this.countedEntitiesService = countedEntitiesService;
    }

    public void getTestDataTweets(String msg){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_TWEETS,countedEntities);
        List<Tweet> latest =  new ArrayList<>();
        try {
            log.info(msg + "--------------------------------------------------------------------");
            int loopId = 0;
            for (long idTwitter : schedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()) {
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
        taskService.done(task,countedEntities);
    }

    public void getTestDataUser(String msg){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_USER,countedEntities);
        List<org.woehlke.twitterwall.oodm.entities.User> user =  new ArrayList<>();
        try {
            int loopId = 0;
            for (long idTwitter : schedulerProperties.getFacade().getIdTwitterToFetchForUserControllerTest()) {
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
                TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(frontendProperties.getImprintScreenName());
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
        countedEntities = countedEntitiesService.countAll();
        taskService.done(task,countedEntities);
    }

    private final TwitterApiService twitterApiService;

    private final StoreOneTweet storeOneTweet;

    private final StoreUserProfile storeUserProfile;

    private final UserService userService;

    private final TaskService taskService;

    private final SchedulerProperties schedulerProperties;

    private final FrontendProperties frontendProperties;

    private final TwitterProperties twitterProperties;

    private final CountedEntitiesService countedEntitiesService;

    private void logEnv(){
        log.info("--------------------------------------------------------------------");
        log.info("twitter.searchQuery = "+twitterProperties.getSearchQuery());
        log.info("twitterwall.frontend.menuAppName = "+ frontendProperties.getMenuAppName());
        log.info("twitterwall.frontend.infoWebpage = "+ frontendProperties.getInfoWebpage());
        log.info("twitterwall.frontend.theme = "+ frontendProperties.getTheme());
        log.info("twitterwall.frontend.contextTest = "+ frontendProperties.getContextTest());
        log.info("twitterwall.frontend.imprintScreenName = "+ frontendProperties.getImprintScreenName());
        log.info("twitterwall.frontend.idGoogleAnalytics = "+ frontendProperties.getIdGoogleAnalytics());
        log.info("--------------------------------------------------------------------");
    }
}
