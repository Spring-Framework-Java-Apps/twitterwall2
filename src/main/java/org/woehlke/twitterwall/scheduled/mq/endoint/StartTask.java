package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.woehlke.twitterwall.oodm.entities.User;

public interface StartTask {

    void fetchTweetsFromTwitterSearch();

    void updateTweets();

    void updateUserProfiles();

    void updateUserProfilesFromMentions();

    void fetchUsersFromDefinedUserList();

    User createImprintUser();

    void createTestDataForUser();

    void createtestDataForTweets();

}
