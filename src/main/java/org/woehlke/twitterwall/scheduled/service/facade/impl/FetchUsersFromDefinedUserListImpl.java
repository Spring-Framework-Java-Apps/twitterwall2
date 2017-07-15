package org.woehlke.twitterwall.scheduled.service.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.facade.FetchUsersFromDefinedUserList;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForScreenName;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForUserList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class FetchUsersFromDefinedUserListImpl implements FetchUsersFromDefinedUserList {


    @Override
    public void fetchUsersFromDefinedUserList() {
        String msg = "update Tweets: ";
        Task task = taskService.create(msg, TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST);
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "START The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
        int allLoop = 0;
        int loopId = 0;
        List<TwitterProfile> userProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUserListName);
        int number = userProfiles.size();
        for(TwitterProfile twitterProfile:userProfiles) {
            allLoop++;
            loopId++;
            String counter = " ( "+loopId+ " from "+number+" ) ["+allLoop+"] ";
            log.debug(msg+counter);
            User user = storeUserProfileForUserList.storeUserProfileForUserList(twitterProfile,task);
            log.debug(msg+counter+user.toString());
            int subLoopId = 0;
            int subNumber = user.getEntities().getMentions().size();
            for(Mention mention:user.getEntities().getMentions()){
                allLoop++;
                subLoopId++;
                String subCounter = counter+" ( "+subLoopId+ "from "+subNumber+" ) ["+allLoop+"] ";
                try {
                    User userFromMention = storeUserProfileForScreenName.storeUserProfileForScreenName(mention.getScreenName(),task);
                    log.debug(msg+subCounter+userFromMention.toString());
                } catch (IllegalArgumentException exe){
                    taskService.warn(task,exe,msg+subCounter);
                }
            }
        }
        String report = msg+" processed: "+loopId+" [ "+allLoop+" ] ";
        taskService.event(task,report);
        taskService.done(task);
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "DONE The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
    }


    private static final Logger log = LoggerFactory.getLogger(FetchUsersFromDefinedUserListImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitterwall.backend.twitter.millisToWaitForFetchTweetsFromTwitterSearch}")
    private int millisToWaitForFetchTweetsFromTwitterSearch;

    @Value("${twitterwall.scheduler.fetchUserList.name}")
    private String fetchUserListName;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    private final StoreUserProfileForUserList storeUserProfileForUserList;

    private final TwitterApiService twitterApiService;

    private final TaskService taskService;

    private final StoreUserProfileForScreenName storeUserProfileForScreenName;

    @Autowired
    public FetchUsersFromDefinedUserListImpl(StoreUserProfileForUserList storeUserProfileForUserList, TwitterApiService twitterApiService, TaskService taskService, StoreUserProfileForScreenName storeUserProfileForScreenName) {
        this.storeUserProfileForUserList = storeUserProfileForUserList;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
        this.storeUserProfileForScreenName = storeUserProfileForScreenName;
    }
}
