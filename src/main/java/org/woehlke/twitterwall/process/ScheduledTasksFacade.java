package org.woehlke.twitterwall.process;

/**
 * Created by tw on 19.06.17.
 */
public interface ScheduledTasksFacade {

    void fetchTweetsFromTwitterSearch();

    void fetchFollowersFromTwitter();

    void fetchFriendsFromTwitter();
}
