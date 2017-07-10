package org.woehlke.twitterwall.scheduled.service.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfileForUserList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 09.07.17.
 */
@Service
public class FetchUsersFromDefinedUserListImpl implements FetchUsersFromDefinedUserList {


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

    @Autowired
    public FetchUsersFromDefinedUserListImpl(StoreUserProfileForUserList storeUserProfileForUserList, TwitterApiService twitterApiService, TaskService taskService) {
        this.storeUserProfileForUserList = storeUserProfileForUserList;
        this.twitterApiService = twitterApiService;
        this.taskService = taskService;
    }

    @Override
    public void fetchUsersFromDefinedUserList() {
        String msg = "update Tweets: ";
        Task task = taskService.create(msg, TaskType.FETCH_USERS_FROM_DEFINED_USER_LIST);
        log.debug(msg + "---------------------------------------");
        log.debug(msg + "The time is now {}", dateFormat.format(new Date()));
        log.debug(msg + "---------------------------------------");
        List<TwitterProfile> userProfiles = twitterApiService.findUsersFromDefinedList(imprintScreenName,fetchUserListName);
        for(TwitterProfile twitterProfile:userProfiles) {
            User user = storeUserProfileForUserList.storeUserProfileForUserList(twitterProfile,task);
        }
        taskService.done(task);
    }
}
