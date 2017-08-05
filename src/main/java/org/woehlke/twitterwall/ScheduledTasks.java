package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.scheduled.mq.endpoint.StartTask;

/**
 * Created by tw on 10.06.17.
 */

@Component
//@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ScheduledTasks {

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS)
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets From TwitterSearch ";
        if(schedulerProperties.getAllowUpdateTweets()  && !schedulerProperties.getSkipFortesting()) {
            startTask.fetchTweetsFromSearch();
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS)
    public void updateTweets() {
        String msg = "update Tweets ";
        if(schedulerProperties.getAllowUpdateTweets() && !schedulerProperties.getSkipFortesting()){
            startTask.updateTweets();
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER)
    public void updateUserProfiles() {
        String msg = "update User Profiles ";
        if(schedulerProperties.getAllowUpdateUserProfiles()  && !schedulerProperties.getSkipFortesting()) {
            startTask.updateUsers();
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION)
    public void updateUserProfilesFromMentions(){
        String msg = "update User Profiles From Mentions";
        if(schedulerProperties.getAllowUpdateUserProfilesFromMention() && !schedulerProperties.getSkipFortesting()) {
            startTask.updateUsersFromMentions();
        }
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST)
    public void fetchUsersFromDefinedUserList(){
        String msg = "fetch Users from Defined User List ";
        if(schedulerProperties.getFetchUserListAllow()  && !schedulerProperties.getSkipFortesting()) {
            startTask.fetchUsersFromList();
        }
    }

    @Autowired
    public ScheduledTasks(SchedulerProperties schedulerProperties, StartTask startTask) {
        this.schedulerProperties = schedulerProperties;
        this.startTask = startTask;
    }

    public final static long EINE_MINUTE = 60 * 1000;

    public final static long FUENF_MINUTEN = 5 * EINE_MINUTE;

    public final static long EINE_STUNDE = 60 * EINE_MINUTE;

    public final static long ZWOELF_STUNDEN = 12 * EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS = ZWOELF_STUNDEN;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION = EINE_STUNDE;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST = ZWOELF_STUNDEN;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final SchedulerProperties schedulerProperties;

    private final StartTask startTask;
}
