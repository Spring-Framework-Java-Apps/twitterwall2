package org.woehlke.twitterwall.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created by tw on 01.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UserServiceTestImpl implements UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTestImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TwitterApiService twitterApiService;

    @Autowired
    private StoreUserProfile storeUserProfile;

    @Autowired
    private TwitterwallFrontendProperties twitterwallFrontendProperties;

    @Override
    public User createImprintUser(){
        return createUser(twitterwallFrontendProperties.getImprintScreenName());
    }

    @Override
    public User createUser(String screenName) {
        String msg = "createUser for screenName="+screenName;
        Task task = taskService.create(msg, TaskType.CONTROLLER_GET_TESTDATA_USER);
        log.info("-----------------------------------------");
        try {
            log.info("screenName = "+ screenName);
            User user = userService.findByScreenName(screenName);
            log.info("userService.findByScreenName: found User = "+user.toString());
            log.info("model.addAttribute user = "+user.toString());
            return user;
        } catch (EmptyResultDataAccessException e){
            log.info("EmptyResultDataAccessException at userService.findByScreenName for screenName="+screenName);
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
            log.info("twitterApiService.getUserProfileForScreenName: found TwitterProfile = "+twitterProfile.toString());
            try {
                log.info("try: persistDataFromTwitter.storeUserProfile for twitterProfile = "+twitterProfile.toString());
                User user = storeUserProfile.storeUserProfile(twitterProfile,task);
                log.info("persistDataFromTwitter.storeUserProfile: stored User = "+user.toString());
                log.info("model.addAttribute user = "+user.toString());
                return user;
            } catch (EmptyResultDataAccessException ex){
                log.warn("persistDataFromTwitter.storeUserProfile raised EmptyResultDataAccessException: "+ex.getMessage());
                User user = getDummyUser(task);
                log.info("model.addAttribute user = "+user.toString());
                return user;
            } catch (NoResultException exe) {
                log.warn("persistDataFromTwitter.storeUserProfile raised NoResultException: "+exe.getMessage());
                User user = getDummyUser(task);
                log.info("model.addAttribute user = "+user.toString());
                return user;
            }
        }  finally {
            taskService.done(task);
            log.info("... finally done ...");
            log.info("-----------------------------------------");
        }
    }

    private User getDummyUser(Task task){
        long idTwitter=0L;
        String screenName = twitterwallFrontendProperties.getImprintScreenName();
        String name="Exception Handler Dummy Username";
        String url="https://github.com/phasenraum2010/twitterwall2";
        String profileImageUrl="https://avatars2.githubusercontent.com/u/303766?v=3&s=460";
        String description="Exception Handler Dummy Description with some #HashTag an URL like https://thomas-woehlke.blogspot.de/ and an @Mention.";
        String location="Berlin, Germany";
        Date createdDate = new Date();
        User user = new User(idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate,task);
        return user;
    }

}
