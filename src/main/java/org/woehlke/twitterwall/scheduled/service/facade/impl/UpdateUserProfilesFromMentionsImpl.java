package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.TwitterProperties;
import org.woehlke.twitterwall.TwitterwallBackendProperties;
import org.woehlke.twitterwall.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.TwitterwallSchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfilesFromMentions;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

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
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<Mention> allPersMentions =  mentionService.getAll(pageRequest);
            hasNext = allPersMentions.hasNext();
            long number = allPersMentions.getTotalElements();
            for(Mention onePersMentions :allPersMentions) {
                String screenName = onePersMentions.getScreenName();
                if ((screenName != null) && (!screenName.isEmpty())) {
                    loopId++;
                    allLoop++;
                    String counter = " ( " + loopId + " from " + number + " ) [" + allLoop + "] ";
                    TwitterProfile twitterProfile = null;
                    try {
                        twitterProfile = this.twitterApiService.getUserProfileForScreenName(screenName);
                    } catch (RateLimitExceededException e) {
                        task = taskService.error(task, e, msg);
                    }
                    if (twitterProfile != null) {
                        User user = storeUserProfile.storeUserProfile(twitterProfile, task);
                        if (user != null) {
                            log.debug(msg + counter + user.toString());
                            Set<Mention> mentions = user.getEntities().getMentions();
                            int subLoopId = 0;
                            int subNumber = mentions.size();
                            for (Mention mention : mentions) {
                                allLoop++;
                                subLoopId++;
                                String subCounter = counter + " ( " + subLoopId + " from " + subNumber + " ) [" + allLoop + "] ";
                                try {
                                    log.debug(msg + subCounter);
                                    User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(), task);
                                    log.debug(msg + subCounter + userFromMention.toString());
                                } catch (IllegalArgumentException exe) {
                                    log.debug(msg + subCounter + exe.getMessage());
                                }
                            }
                            log.debug(msg + user.toString());
                            log.debug(msg + "-----------------------------------------------------");
                            log.debug(msg + "Start SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                            try {
                                Thread.sleep(twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls());
                            } catch (InterruptedException ex) {
                                log.warn(msg, ex);
                            }

                            log.debug(msg + "Done SLEEP for " + twitterwallBackendProperties.getTwitter().getMillisToWaitBetweenTwoApiCalls() + " ms " + counter);
                            log.debug(msg + "-----------------------------------------------------");
                        }
                    }
                }
            }
            pageRequest = pageRequest.next();
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

    private final StoreUserProfile storeUserProfile;

    private final TwitterApiService twitterApiService;

    private final MentionService mentionService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    private final TwitterwallBackendProperties twitterwallBackendProperties;

    private final TwitterwallSchedulerProperties twitterwallSchedulerProperties;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterProperties twitterProperties;

    @Autowired
    public UpdateUserProfilesFromMentionsImpl(TwitterApiService twitterApiService, StoreUserProfile storeUserProfile, MentionService mentionService, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName, TwitterwallBackendProperties twitterwallBackendProperties, TwitterwallSchedulerProperties twitterwallSchedulerProperties, TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterProperties twitterProperties) {
        this.twitterApiService = twitterApiService;
        this.storeUserProfile = storeUserProfile;
        this.mentionService = mentionService;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
        this.twitterwallBackendProperties = twitterwallBackendProperties;
        this.twitterwallSchedulerProperties = twitterwallSchedulerProperties;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterProperties = twitterProperties;
    }
}
