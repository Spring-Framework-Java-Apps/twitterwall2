package org.woehlke.twitterwall.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.woehlke.twitterwall.process.ScheduledTasksFacade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public ScheduledTasks(ScheduledTasksFacade scheduledTasksFacade) {
        this.scheduledTasksFacade = scheduledTasksFacade;
    }

    private final ScheduledTasksFacade scheduledTasksFacade;

    private final static long FIXED_RATE_FOR_SCHEDULAR_MINUTEN = 5;

    private final static long FIXED_RATE_FOR_SCHEDULAR = FIXED_RATE_FOR_SCHEDULAR_MINUTEN * 60 * 1000;

    private final static long FIXED_RATE_FOR_SCHEDULAR_MINUTEN_USER = 5; //12 * 60;

    private final static long FIXED_RATE_FOR_SCHEDULAR_USER = FIXED_RATE_FOR_SCHEDULAR_MINUTEN_USER * 60 * 1000;

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR)
    public void fetchTweetsFromTwitterSearch() {
        log.info("ScheduledTasks.fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));

        this.scheduledTasksFacade.fetchTweetsFromTwitterSearch();
    }

    @Scheduled(fixedRate = FIXED_RATE_FOR_SCHEDULAR_USER)
    public void updateUserProfiles(){
        log.info("ScheduledTasks.updateUserProfiles: The time is now {}", dateFormat.format(new Date()));

        this.scheduledTasksFacade.updateUserProfiles();
    }

    //@Scheduled(fixedRate = 12000000)
    public void fetchFollowersFromTwitter() {
        this.scheduledTasksFacade.fetchFollowersFromTwitter();
    }

    //@Scheduled(fixedRate = 12000000)
    public void fetchFriendsFromTwitter() {
        this.scheduledTasksFacade.fetchFriendsFromTwitter();
    }
}
