package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.facade.CreateTestData;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 18.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class CreateTestDataImpl implements CreateTestData {

    /*
    public final static long[] ID_TWITTER_TO_FETCH_FOR_TWEET_TEST = {
            876329508009279488L,
            876356335784394752L,
            876676270913880066L,
            876566077836337152L,
            876563676395962368L,
            876514968933478400L,
            876514568671023104L,
            876513930478313472L,
            876510758632386563L,
            876496934676180992L
    };
    public final static long[] ID_TWITTER_TO_FETCH_FOR_PROFILE_CONTROLLER_TEST = {
            876433563561938944L, // t3c_berlin
            876456051016597504L, // Codemonkey1988
            876420491329892354L, // Walter_kran
            876425220147564544L, // tobenschmidt
            876819177746649088L, // Oliver1973
            876514968933478400L, // wowa_TYPO3
            876441863191920641L, // dirscherl17
            876441015523192832L, // Markus306
            876440419416109056L  // mattLefaux
    };
    */


    public org.springframework.data.domain.Page<Tweet> getTestDataTweets(){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        String msg = "getTestDataTweets: ";
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_TWEETS,countedEntities);
        List<Tweet> latest =  new ArrayList<>();
        try {
            log.info(msg + "--------------------------------------------------------------------");
            int loopId = 0;
            for (long idTwitter : twitterwallSchedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()) {
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
        int numberOfTwweets = latest.size();
        if(numberOfTwweets > 0 ) {
            Pageable pageRequest = new PageRequest(0, numberOfTwweets);
            org.springframework.data.domain.Page<Tweet> result = new PageImpl(latest, pageRequest, numberOfTwweets);
            //model.addAttribute("latestTweets", result);
            return result;
        } else {
            return null;
            //model.addAttribute("latestTweets",null);
        }
    }


    public org.springframework.data.domain.Page<org.woehlke.twitterwall.oodm.entities.User> getTestDataUser(){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        String msg = "getTestDataUser: ";
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_USER,countedEntities);
        List<org.woehlke.twitterwall.oodm.entities.User> user =  new ArrayList<>();
        try {
            int loopId = 0;
            for (long idTwitter : twitterwallSchedulerProperties.getFacade().getIdTwitterToFetchForUserControllerTest()) {
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
        } catch (RateLimitExceededException e) {
            log.info(msg + e.getMessage());
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
        }
        taskService.done(task,countedEntities);
        //model.addAttribute("user", user);

        int numberOfUser = user.size();
        if(numberOfUser > 0) {
            Pageable pageRequest = new PageRequest(0, numberOfUser);
            org.springframework.data.domain.Page<org.woehlke.twitterwall.oodm.entities.User> result = new PageImpl(user, pageRequest, numberOfUser);
            //model.addAttribute("user", result);
            return result;
        } else {
            //model.addAttribute("user",null);
            return null;
        }
    }

    public User addUserForScreenName(String screenName) {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        User userReturnValue = null;
        String msg="addUserForScreenName "+screenName+": ";
        Task task = taskService.create(msg, TaskType.CONTROLLER_ADD_USER_FOR_SCREEN_NAME,countedEntities);
        log.info("--------------------------------------------------------------------");
        log.info("screenName = "+ screenName);
        User user = userService.findByScreenName(screenName);
        if(user!=null){
            log.info("userService.findByScreenName: found User = "+user.toString());
            //model.addAttribute("user", user);
            userReturnValue = user;
            //log.info("model.addAttribute user = "+user.toString());
        } else {
            String msg2 = "EmptyResultDataAccessException at userService.findByScreenName for screenName="+screenName;
            task = taskService.warn(task,msg2,countedEntities);
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
            String msg3 = "twitterApiService.getUserProfileForScreenName: found TwitterProfile = "+twitterProfile.toString();
            task = taskService.event(task,msg3,countedEntities);
            log.info("try: persistDataFromTwitter.storeUserProfile for twitterProfile = "+twitterProfile.toString());
            User user2 = storeUserProfile.storeUserProfile(twitterProfile,task);
            if(user2!=null){
                log.info("persistDataFromTwitter.storeUserProfile: stored User = "+user2.toString());
                //model.addAttribute("user", user2);
                //log.info("model.addAttribute user = "+user2.toString());
                userReturnValue = user2;
            } else {
                log.warn("persistDataFromTwitter.storeUserProfile raised EmptyResultDataAccessException: ");
                User user3 = User.getDummyUserForScreenName(screenName,task);
                //model.addAttribute("user", user3);
                //log.info("model.addAttribute user = "+user3.toString());
                userReturnValue = user3;
            }
        }
        taskService.done(task,countedEntities);
        log.info("... finally done ...");
        log.info("--------------------------------------------------------------------");
        return userReturnValue;
    }

    @Autowired
    public CreateTestDataImpl(TwitterApiService twitterApiService, TaskService taskService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, UserService userService, TwitterwallSchedulerProperties twitterwallSchedulerProperties, CountedEntitiesService countedEntitiesService) {
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.userService = userService;
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.countedEntitiesService = countedEntitiesService;
    }

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final StoreOneTweet storeOneTweet;

    private final StoreUserProfile storeUserProfile;

    private final UserService userService;

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final CountedEntitiesService countedEntitiesService;

    private static final Logger log = LoggerFactory.getLogger(CreateTestDataImpl.class);

}
