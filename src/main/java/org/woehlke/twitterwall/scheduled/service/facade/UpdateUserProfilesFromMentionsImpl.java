package org.woehlke.twitterwall.scheduled.service.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskType;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.application.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateUserProfilesFromMentionsImpl implements UpdateUserProfilesFromMentions {


    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesFromMentionsImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;


    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final MentionService mentionService;

    private final TaskService taskService;

    @Autowired
    public UpdateUserProfilesFromMentionsImpl(TwitterApiService twitterApiService, StoreUserProfile storeUserProfile, MentionService mentionService, CountedEntitiesService countedEntitiesService, TaskService taskService) {
        this.twitterApiService = twitterApiService;
        this.storeUserProfile = storeUserProfile;
        this.mentionService = mentionService;
        this.taskService = taskService;
    }

    @Override
    public void updateUserProfilesFromMentions(){
        String msg = "update User Profiles from Mentions: ";
        log.debug(msg + "START - The time is now {}", dateFormat.format(new Date()));
        Task task = this.taskService.create(msg, TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS);
        try {
            List<Mention> allPersMentions =  mentionService.getAll();
            for(Mention onePersMentions :allPersMentions){
                String screenName = onePersMentions.getScreenName();
                if((screenName != null) && (!screenName.isEmpty())) {
                    try {
                        TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                        User user = storeUserProfile.storeUserProfile(twitterProfile);
                        log.debug(msg + user.toString());
                    } catch (RateLimitExceededException e) {
                        log.warn(msg + e.getMessage());
                        Throwable t = e.getCause();
                        while(t != null){
                            log.warn(msg + t.getMessage());
                            t = t.getCause();
                        }
                        throw new TwitterApiException(msg+screenName, e);
                    } catch (TwitterApiException e) {
                        log.warn(msg + e.getMessage());
                        Throwable t = e.getCause();
                        while(t != null){
                            log.warn(msg + t.getMessage());
                            t = t.getCause();
                        }
                        log.debug(msg+screenName + e.getMessage());
                    } finally {
                        log.debug(msg +"---------------------------------------");
                    }
                }
            }
        } catch (ResourceAccessException e) {
            log.warn(msg + " ResourceAccessException: " + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + " caused by: "+ t.getMessage());
                t = t.getCause();
            }
            log.error(msg + " check your Network Connection!");
            throw e;
        } catch (RateLimitExceededException e) {
            throw e;
        } catch (RuntimeException e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } catch (Exception e) {
            log.warn(msg + e.getMessage());
            Throwable t = e.getCause();
            while(t != null){
                log.warn(msg + t.getMessage());
                t = t.getCause();
            }
            throw e;
        } finally {
            log.debug(msg +"---------------------------------------");
            this.taskService.done(task);
        }
        log.debug(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }
}
