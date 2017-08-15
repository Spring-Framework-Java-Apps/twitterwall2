package org.woehlke.twitterwall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.scheduled.mq.endpoint.AsyncStartTask;

/**
 * Created by tw on 10.06.17.
 */
@Component
public class ScheduledTasks {

    @Scheduled(initialDelay= TEN_SECONDS, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS)
    public void fetchTweetsFromTwitterSearch() {
        String msg = "fetch Tweets From TwitterSearch ";
        if((schedulerProperties.getAllowUpdateTweets())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.fetchTweetsFromSearch();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *2, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST)
    public void fetchUsersFromDefinedUserList(){
        String msg = "fetch Users from Defined User List ";
        if((schedulerProperties.getFetchUserListAllow()) && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.fetchUsersFromList();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *3, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_FOLLOWER)
    public void fetchFollower(){
        String msg = "fetch Follower ";
        if((schedulerProperties.getFetchFollowerAllow())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.fetchFollower();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *4, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_FRIENDS)
    public void fetchFriends(){
        String msg = "fetch Friends ";
        if((schedulerProperties.getFetchFriendsAllow()) && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.fetchFriends();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *5, fixedRate = FIXED_RATE_FOR_SCHEDULAR_REMOVE_OLD_DATA_FROM_STORAGE)
    public void removeOldDataFromStorage(){
        String msg = "remove Old Data From Storage: ";
        if((schedulerProperties.getRemoveOldDataFromStorageAllow())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.removeOldDataFromStorage();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *6, fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION)
    public void updateUserProfilesFromMentions(){
        String msg = "update User Profiles From Mentions";
        if((schedulerProperties.getAllowUpdateUserProfilesFromMention()) && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.updateUsersFromMentions();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *7, fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS)
    public void updateTweets() {
        String msg = "update Tweets ";
        if((schedulerProperties.getAllowUpdateTweets()) && (!schedulerProperties.getSkipFortesting())){
            Task task = asyncStartTask.updateTweets();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *8, fixedRate = FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER)
    public void updateUserProfiles() {
        String msg = "update User Profiles ";
        if((schedulerProperties.getAllowUpdateUserProfiles())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.updateUsers();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *9, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_HOME_TIMELINE)
    public void getHomeTimeline() {
        String msg = "get Home Timeline Tweets ";
        if((schedulerProperties.getAllowGetHomeTimeline())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getHomeTimeline();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *10, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_USER_TIMELINE)
    public void getUserTimeline() {
        String msg = " get User Timeline Tweets ";
        if((schedulerProperties.getAllowGetUserTimeline())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getUserTimeline();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *11, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_MENTIONS)
    public void getMentions() {
        String msg = " get Mentions ";
        if((schedulerProperties.getAllowGetMentions())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getMentions();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *12, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_FAVORITES)
    public void getFavorites() {
        String msg = " get Favorites ";
        if((schedulerProperties.getAllowGetFavorites())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getFavorites();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *13, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_RETWEETS_OF_ME)
    public void getRetweetsOfMe() {
        String msg = " get Retweets Of Me ";
        if((schedulerProperties.getAllowGetRetweetsOfMe())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getRetweetsOfMe();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Scheduled(initialDelay= TEN_SECONDS *14, fixedRate = FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_USER_LISTS)
    public void getLists() {
        String msg = " get Lists ";
        if((schedulerProperties.getAllowGetLists())  && (!schedulerProperties.getSkipFortesting())) {
            Task task = asyncStartTask.getLists();
            log.info(msg+ "SCHEDULED: task "+task.getUniqueId());
        }
    }

    @Autowired
    public ScheduledTasks(SchedulerProperties schedulerProperties, AsyncStartTask mqAsyncStartTask) {
        this.schedulerProperties = schedulerProperties;
        this.asyncStartTask = mqAsyncStartTask;
    }

    public final static long TEN_SECONDS = 10 * 1000;

    public final static long ONE_MINUTE = 60 * 1000;

    public final static long ONE_HOUR = 60 * ONE_MINUTE;

    public final static long TWELVE_HOURS = 12 * ONE_HOUR;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS = ONE_HOUR;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_FOLLOWER = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_FRIENDS = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_TWEETS = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_UPDATE_USER_BY_MENTION = ONE_HOUR;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_USER_LISTS = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_FAVORITES = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_RETWEETS_OF_ME = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_MENTIONS = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_USER_TIMELINE = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_TWEETS_HOME_TIMELINE = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_FETCH_USER_LIST = TWELVE_HOURS;

    private final static long FIXED_RATE_FOR_SCHEDULAR_REMOVE_OLD_DATA_FROM_STORAGE = ONE_HOUR;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final SchedulerProperties schedulerProperties;

    private final AsyncStartTask asyncStartTask;
}
