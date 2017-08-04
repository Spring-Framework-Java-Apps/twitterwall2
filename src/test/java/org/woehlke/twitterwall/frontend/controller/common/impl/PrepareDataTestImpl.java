package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.remote.TwitterApiService;
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
    public PrepareDataTestImpl(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, TaskService taskService, UserService userService, TweetService tweetService, TestdataProperties testdataProperties, FrontendProperties frontendProperties, CountedEntitiesService countedEntitiesService) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.taskService = taskService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.testdataProperties = testdataProperties;
        this.frontendProperties = frontendProperties;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public void getTestDataTweets(String msg){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, TaskType.CONTROLLER_CREATE_TESTDATA_TWEETS,countedEntities);
        List<Tweet> latest =  new ArrayList<>();
        try {
            log.info(msg + "--------------------------------------------------------------------");
            int loopId = 0;
            List<Long> idTwitterListTweets = testdataProperties.getOodm().getEntities().getTweet().getIdTwitter();
            for (long idTwitter : idTwitterListTweets) {
                try {
                    org.woehlke.twitterwall.oodm.entities.Tweet persTweet = tweetService.findByIdTwitter(idTwitter);
                    if(persTweet != null){
                        loopId++;
                        log.info(msg + "--------------------------------------------------------------------");
                        log.info(msg + loopId + " " + persTweet.getUniqueId());
                        log.info(msg + "--------------------------------------------------------------------");
                        latest.add(persTweet);
                    } else {
                        org.springframework.social.twitter.api.Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                        if (tweet != null) {
                            persTweet = this.storeOneTweet.storeOneTweet(tweet, task);
                            if (persTweet != null) {
                                loopId++;
                                log.info(msg + "--------------------------------------------------------------------");
                                log.info(msg + loopId + " " + persTweet.getUniqueId());
                                log.info(msg + "--------------------------------------------------------------------");
                                latest.add(persTweet);
                            }
                        }
                    }
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

    @Override
    public void getTestDataUser(String msg){
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, TaskType.CONTROLLER_CREATE_TESTDATA_USERS,countedEntities);
        List<org.woehlke.twitterwall.oodm.entities.User> user =  new ArrayList<>();
        try {
            int loopId = 0;
            List<Long> idTwitterListUsers = testdataProperties.getOodm().getEntities().getUser().getIdTwitter();
            for (long idTwitter : idTwitterListUsers) {
                try {
                    org.woehlke.twitterwall.oodm.entities.User persUser = userService.findByIdTwitter(idTwitter);
                    if(persUser != null){
                        loopId++;
                        user.add(persUser);
                        log.debug(msg + loopId + " " + persUser.getUniqueId());
                    } else {
                        TwitterProfile twitterProfile = twitterApiService.getUserProfileForTwitterId(idTwitter);
                        if (twitterProfile != null) {
                            persUser = this.storeUserProfile.storeUserProfile(twitterProfile, task);
                            if (persUser != null) {
                                loopId++;
                                user.add(persUser);
                                log.debug(msg + loopId + " " + persUser.getUniqueId());
                            }
                        }
                    }
                } catch (ResourceNotFoundException ee){
                    log.warn(msg + ee.getMessage());
                }catch (EmptyResultDataAccessException e) {
                    log.warn(msg + e.getMessage());
                } catch (NoResultException e) {
                    log.warn(msg + e.getMessage());
                }
            }
            try {
                org.woehlke.twitterwall.oodm.entities.User persUser = userService.findByScreenName(frontendProperties.getImprintScreenName());
                if(persUser!=null){
                    loopId++;
                    user.add(persUser);
                    log.debug(msg + loopId + " " + persUser.getUniqueId());
                } else {
                    TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(frontendProperties.getImprintScreenName());
                    if (twitterProfile != null) {
                        persUser = this.storeUserProfile.storeUserProfile(twitterProfile, task);
                        if (persUser != null) {
                            loopId++;
                            user.add(persUser);
                            log.debug(msg + loopId + " " + persUser.getUniqueId());
                        }
                    }
                }
            } catch (ResourceNotFoundException ee){
                log.warn(msg + ee.getMessage());
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

    private final TaskService taskService;

    private final UserService userService;

    private final TweetService tweetService;

    private final TestdataProperties testdataProperties;

    private final FrontendProperties frontendProperties;

    private final CountedEntitiesService countedEntitiesService;

}
