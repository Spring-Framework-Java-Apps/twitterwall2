package org.woehlke.twitterwall.scheduled.mq.endoint;

public interface AsyncStartTask {

    void updateTweets();

    void updateUserProfiles();

    void updateUserProfilesFromMentions();

    void fetchTweetsFromTwitterSearch();

    void fetchUsersFromDefinedUserList();

}
