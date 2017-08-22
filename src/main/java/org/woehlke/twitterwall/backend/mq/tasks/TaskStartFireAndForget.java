package org.woehlke.twitterwall.backend.mq.tasks;

import org.woehlke.twitterwall.oodm.model.Task;


public interface TaskStartFireAndForget {

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

    Task fetchUserlistOwners();

    Task startGarbageCollection();

    Task startUpdateUrls();

    Task startFetchListOwner();

    Task startFetchListsForUsers();
}
