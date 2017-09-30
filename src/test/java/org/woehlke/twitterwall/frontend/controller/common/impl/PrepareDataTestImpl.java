package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.backend.service.remote.TwitterApiService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.backend.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.backend.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 13.07.17.
 */
@Deprecated
@Component
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
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskType taskType = TaskType.CREATE_TESTDATA_TWEETS;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, taskType, taskSendType, countedEntities);
        List<Tweet> latest =  new ArrayList<>();
        try {
            log.debug(msg + "--------------------------------------------------------------------");
            int loopId = 0;
            List<Long> idTwitterListTweets = testdataProperties.getOodm().getEntities().getTweet().getIdTwitter();
            for (long idTwitter : idTwitterListTweets) {
                try {
                    org.woehlke.twitterwall.oodm.model.Tweet persTweet = tweetService.findByIdTwitter(idTwitter);
                    if(persTweet != null){
                        loopId++;
                        log.debug(msg + "--------------------------------------------------------------------");
                        log.debug(msg + loopId + " " + persTweet.getUniqueId());
                        log.debug(msg + "--------------------------------------------------------------------");
                        latest.add(persTweet);
                    } else {
                        org.springframework.social.twitter.api.Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                        if (tweet != null) {
                            persTweet = this.storeOneTweet.storeOneTweet(tweet, task);
                            if (persTweet != null) {
                                loopId++;
                                log.debug(msg + "--------------------------------------------------------------------");
                                log.debug(msg + loopId + " " + persTweet.getUniqueId());
                                log.debug(msg + "--------------------------------------------------------------------");
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
            log.debug(msg + e.getMessage());
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
        } finally {
            log.debug(msg + "--------------------------------------------------------------------");
        }
        for(Tweet tweet:latest){
            log.debug(msg + tweet.toString());
        }
        taskService.done(task,countedEntities);
    }

    @Override
    public void getTestDataUser(String msg){
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskType taskType = TaskType.CREATE_TESTDATA_USERS;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task task = taskService.create(msg, taskType, taskSendType,countedEntities);
        List<org.woehlke.twitterwall.oodm.model.User> user =  new ArrayList<>();
        try {
            int loopId = 0;
            List<Long> idTwitterListUsers = testdataProperties.getOodm().getEntities().getUser().getIdTwitter();
            for (long idTwitter : idTwitterListUsers) {
                try {
                    org.woehlke.twitterwall.oodm.model.User persUser = userService.findByIdTwitter(idTwitter);
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
                org.woehlke.twitterwall.oodm.model.User persUser = userService.findByScreenName(frontendProperties.getImprintScreenName());
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
            log.debug(msg + e.getMessage());
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
        }
        for(org.woehlke.twitterwall.oodm.model.User oneUser:user){
            log.debug(msg + oneUser.toString());
        }
        countedEntities = countedEntitiesService.countAll();
        taskService.done(task,countedEntities);
    }

    @Override
    public User createUser(String screenName) {
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        TaskType taskType = TaskType.CREATE_TESTDATA_USERS;
        CountedEntities countedEntities = countedEntitiesService.countAll();
        String msg = "createUser for screenName="+screenName;
        Task task = taskService.create(msg, taskType, taskSendType, countedEntities);
        log.debug("-----------------------------------------");
        try {
            log.debug("screenName = "+ screenName);
            User user = userService.findByScreenName(screenName);
            log.debug("userService.findByScreenName: found User = "+user.toString());
            log.debug("model.addAttribute user = "+user.toString());
            return user;
        } catch (EmptyResultDataAccessException e){
            log.debug("EmptyResultDataAccessException at userService.findByScreenName for screenName="+screenName);
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
            log.debug("twitterApiService.getUserProfileForScreenName: found TwitterProfile = "+twitterProfile.toString());
            try {
                log.debug("try: persistDataFromTwitter.storeUserProfile for twitterProfile = "+twitterProfile.toString());
                User user = storeUserProfile.storeUserProfile(twitterProfile,task);
                log.debug("persistDataFromTwitter.storeUserProfile: stored User = "+user.toString());
                log.debug("model.addAttribute user = "+user.toString());
                return user;
            } catch (EmptyResultDataAccessException ex){
                log.warn("persistDataFromTwitter.storeUserProfile raised EmptyResultDataAccessException: "+ex.getMessage());
                User user = getDummyUser(task);
                log.debug("model.addAttribute user = "+user.toString());
                return user;
            } catch (NoResultException exe) {
                log.warn("persistDataFromTwitter.storeUserProfile raised NoResultException: "+exe.getMessage());
                User user = getDummyUser(task);
                log.debug("model.addAttribute user = "+user.toString());
                return user;
            }
        }  finally {
            countedEntities = countedEntitiesService.countAll();
            taskService.done(task,countedEntities);
            log.debug("... finally done ...");
            log.debug("-----------------------------------------");
        }
    }

    private User getDummyUser(Task task){
        long idTwitter=0L;
        String screenName = frontendProperties.getImprintScreenName();
        String name="Exception Handler Dummy Username";
        String url="https://github.com/phasenraum2010/twitterwall2";
        String profileImageUrl="https://avatars2.githubusercontent.com/u/303766?v=3&s=460";
        String description="Exception Handler Dummy Description with some #HashTag an URL like https://thomas-woehlke.blogspot.de/ and an @Mention.";
        String location="Berlin, Germany";
        Date createdDate = new Date();
        User user = new User(task,null,idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate);
        return user;
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
