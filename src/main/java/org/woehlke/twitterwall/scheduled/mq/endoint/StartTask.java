package org.woehlke.twitterwall.scheduled.mq.endoint;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;

import java.util.List;

public interface StartTask {

    void fetchTweetsFromTwitterSearch();

    void updateTweets();

    void updateUserProfiles();

    void updateUserProfilesFromMentions();

    void fetchUsersFromDefinedUserList();

    User createImprintUser();

    List<User> createTestDataForUser();

    List<Tweet> createTestDataForTweets();

}
