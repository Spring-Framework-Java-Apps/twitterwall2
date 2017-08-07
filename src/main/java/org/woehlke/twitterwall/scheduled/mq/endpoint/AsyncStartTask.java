package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.woehlke.twitterwall.oodm.entities.Task;

public interface AsyncStartTask {

    Task updateTweets();

    Task updateUsers();

    Task updateUsersFromMentions();

    Task fetchTweetsFromSearch();

    Task fetchUsersFromList();

    Task fetchFollower();

    Task createTestDataForTweets();

    Task createTestDataForUser();

    Task removeOldDataFromStorage();
}
