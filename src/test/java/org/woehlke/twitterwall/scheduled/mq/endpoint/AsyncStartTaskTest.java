package org.woehlke.twitterwall.scheduled.mq.endpoint;


/**
 * @see org.woehlke.twitterwall.scheduled.mq.endpoint.AsyncStartTask
 */
public interface AsyncStartTaskTest {

    void updateTweetsTest() throws Exception;

    void updateUsersTest() throws Exception;

    void updateUsersFromMentionsTest() throws Exception;

    void fetchTweetsFromSearchTest() throws Exception;

    void fetchUsersFromListTest() throws Exception;

    void fetchFollowerTest() throws Exception;

    void fetchFriendsTest() throws Exception;

    void removeOldDataFromStorageTest() throws Exception;

    void getHomeTimeline() throws Exception;

    void getUserTimeline() throws Exception;

    void getMentions() throws Exception;

    void getFavorites() throws Exception;

    void getRetweetsOfMe() throws Exception;

    void getLists() throws Exception;
}
