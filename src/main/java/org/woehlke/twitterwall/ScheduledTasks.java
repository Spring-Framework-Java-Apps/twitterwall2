package org.woehlke.twitterwall;

/**
 * Created by tw on 09.07.17.
 */
public interface ScheduledTasks {

    void fetchTweetsFromTwitterSearch();

    void updateTweets();

    void updateUserProfiles();

    void updateUserProfilesFromMentions();

    void fetchUsersFromDefinedUserList();
}
