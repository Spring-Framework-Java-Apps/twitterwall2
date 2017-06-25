package org.woehlke.twitterwall.process.tasks;

/**
 * Created by tw on 19.06.17.
 */
public interface ScheduledTasksFacade {

    void fetchTweetsFromTwitterSearch();

    void updateUserProfiles();

    void updateTweets();
}
