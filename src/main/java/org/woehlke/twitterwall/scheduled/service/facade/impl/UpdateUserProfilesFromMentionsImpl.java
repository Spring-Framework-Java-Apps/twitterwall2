package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfilesFromMentions;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class UpdateUserProfilesFromMentionsImpl implements UpdateUserProfilesFromMentions {

    @Override
    public void updateUserProfilesFromMentions(){
        String msg = "update User Profiles from Mentions: ";
        log.debug(msg + "START - The time is now {}", dateFormat.format(new Date()));
        Task task = this.taskService.create(msg, TaskType.UPDATE_USER_PROFILES_FROM_MENTIONS);
        int allLoop = 0;
        int loopId = 0;
        try {
            boolean hasNext;
            Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, pageSize);
            do {
                Page<Mention> allPersMentions =  mentionService.getAll(pageRequest);
                hasNext = allPersMentions.hasNext();
                long number = allPersMentions.getTotalElements();
                for(Mention onePersMentions :allPersMentions){
                    String screenName = onePersMentions.getScreenName();
                    if((screenName != null) && (!screenName.isEmpty())) {
                        loopId++;
                        allLoop++;
                        String counter = " ( "+loopId+ " from "+number+" ) ["+allLoop+"] ";
                        try {
                            TwitterProfile twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                            User user = storeUserProfile.storeUserProfile(twitterProfile,task);
                            log.debug(msg + counter + user.toString());
                            Set<Mention> mentions = user.getEntities().getMentions();
                            int subLoopId = 0;
                            int subNumber = mentions.size();
                            for(Mention mention:mentions){
                                allLoop++;
                                subLoopId++;
                                String subCounter = counter+" ( "+subLoopId+ " from "+subNumber+" ) ["+allLoop+"] ";
                                try {
                                    log.debug(msg+subCounter);
                                    User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(),task);
                                    log.debug(msg+subCounter+userFromMention.toString());
                                } catch (IllegalArgumentException exe){
                                    log.debug(msg+subCounter+exe.getMessage());
                                }
                            }
                            log.debug(msg + user.toString());
                            log.debug(msg + "-----------------------------------------------------");
                            log.debug(msg + "Start SLEEP for "+millisToWaitBetweenTwoApiCalls+" ms "+counter);
                            Thread.sleep(millisToWaitBetweenTwoApiCalls);
                            log.debug(msg + "Done SLEEP for "+millisToWaitBetweenTwoApiCalls+" ms "+counter);
                            log.debug(msg + "-----------------------------------------------------");
                        } catch (RateLimitExceededException e) {
                            task = taskService.error(task,e,msg);
                            throw e;
                        } catch (InterruptedException ex){
                            task = taskService.warn(task,ex,msg);
                        } finally {
                            log.debug(msg +"---------------------------------------");
                        }
                    }
                }
                pageRequest = pageRequest.next();
            } while (hasNext);
        } catch (ResourceAccessException e) {
            task = taskService.warn(task,e,msg);
        } catch (RateLimitExceededException e) {
            task = taskService.warn(task,e,msg);
        }
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        this.taskService.event(task,report);
        this.taskService.done(task);
        log.debug(msg +"---------------------------------------");
        log.debug(msg + "DONE - The time is now {}", dateFormat.format(new Date()));
        log.debug(msg+"---------------------------------------");
    }


    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesFromMentionsImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitBetweenTwoApiCalls}")
    private int millisToWaitBetweenTwoApiCalls;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final MentionService mentionService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public UpdateUserProfilesFromMentionsImpl(TwitterApiService twitterApiService, StoreUserProfile storeUserProfile, MentionService mentionService, CountedEntitiesService countedEntitiesService, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName) {
        this.twitterApiService = twitterApiService;
        this.storeUserProfile = storeUserProfile;
        this.mentionService = mentionService;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
    }
}
