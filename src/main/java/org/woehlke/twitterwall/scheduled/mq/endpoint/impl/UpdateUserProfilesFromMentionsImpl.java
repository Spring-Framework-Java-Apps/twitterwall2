package org.woehlke.twitterwall.scheduled.mq.endpoint.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.Message;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.UpdateUserProfilesFromMentions;
import org.woehlke.twitterwall.scheduled.mq.msg.TaskMessage;
import org.woehlke.twitterwall.scheduled.mq.msg.TwitterProfileMessage;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

@Component("mqUpdateUserProfilesFromMentions")
public class UpdateUserProfilesFromMentionsImpl implements UpdateUserProfilesFromMentions {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesFromMentionsImpl.class);

    private final TwitterProperties twitterProperties;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final MentionService mentionService;

    private final CountedEntitiesService countedEntitiesService;

    public UpdateUserProfilesFromMentionsImpl(TwitterProperties twitterProperties, TwitterApiService twitterApiService, TaskService taskService, MentionService mentionService, CountedEntitiesService countedEntitiesService) {
        this.twitterProperties = twitterProperties;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.mentionService = mentionService;
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public List<TwitterProfileMessage> splitMessage(Message<TaskMessage> message) {
        String msg ="splitMessage: ";
        log.debug(msg+ " START");
        CountedEntities countedEntities = countedEntitiesService.countAll();
        List<TwitterProfileMessage> userProfileList = new ArrayList<>();
        TaskMessage msgIn = message.getPayload();
        long id = msgIn.getTaskId();
        Task task = taskService.findById(id);
        task =  taskService.start(task,countedEntities);
        List<String> screenNames = new ArrayList<>();
        int lfdNr = 0;
        int all = 0;
        boolean hasNext=true;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, twitterProperties.getPageSize());
        while (hasNext) {
            Page<Mention> allPersMentions = mentionService.getAllWithoutPersistentUser(pageRequest);
            hasNext = allPersMentions.hasNext();
            for (Mention onePersMentions : allPersMentions) {
                if (!onePersMentions.hasPersistentUser()) {
                    lfdNr++;
                    all++;
                    String screenName = onePersMentions.getScreenName();
                    log.debug("### mentionService.getAll from DB ("+lfdNr+"): "+screenName);
                    screenNames.add(screenName);
                }
            }
            pageRequest = pageRequest.next();
        }
        int millisToWaitBetweenTwoApiCalls = twitterProperties.getMillisToWaitBetweenTwoApiCalls();
        for(String screenName:screenNames){
            lfdNr++;
            log.debug("### twitterApiService.getUserProfileForScreenName("+screenName+") from Twiiter API ("+lfdNr+" of "+all+")");
            TwitterProfile userProfile = twitterApiService.getUserProfileForScreenName(screenName);
            if(userProfile!=null) {
                TwitterProfileMessage userMsg = new TwitterProfileMessage(msgIn, userProfile);
                userProfileList.add(userMsg);
            }
            log.debug(msg+"### waiting now for (ms): "+millisToWaitBetweenTwoApiCalls);
            try {
                Thread.sleep(millisToWaitBetweenTwoApiCalls);
            } catch (InterruptedException e) {
            }
        }
        return userProfileList;
    }
}