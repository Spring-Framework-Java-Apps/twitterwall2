package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.woehlke.twitterwall.oodm.entities.Task;


public interface AsyncStartTask {

    Task updateTweets();

    Task updateUsers();

    Task updateUsersFromMentions();

    Task fetchTweetsFromSearch();

    Task fetchUsersFromList();

    Task fetchFollower();

    Task fetchFriends();

    Task createTestDataForTweets();

    Task createTestDataForUser();

    Task removeOldDataFromStorage();

    Task getHomeTimeline();

    Task getUserTimeline();

    Task getMentions();

    Task getFavorites();

    Task getRetweetsOfMe();

    Task getLists();

    Task createImprintUserAsync();
}
