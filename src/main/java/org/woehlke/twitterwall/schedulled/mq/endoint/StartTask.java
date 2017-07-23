package org.woehlke.twitterwall.schedulled.mq.endoint;

public interface StartTask {

    void fetchTweetsFromTwitterSearch();

    void updateTweets();

    void updateUserProfiles();

    void updateUserProfilesFromMentions();

    void fetchUsersFromDefinedUserList();

    void createImprintUser();

    void createTestDataForUser();

    void createtestDataForTweets();

}
