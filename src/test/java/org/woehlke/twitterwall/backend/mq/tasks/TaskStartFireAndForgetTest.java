package org.woehlke.twitterwall.backend.mq.tasks;


/**
 * @see TaskStartFireAndForget
 */
public interface TaskStartFireAndForgetTest {

    void test010updateTweetsTest() throws Exception;

    void test011updateUsersTest() throws Exception;

    void test012updateUsersFromMentionsTest() throws Exception;

    void test013fetchTweetsFromSearchTest() throws Exception;

    void test014fetchUsersFromListTest() throws Exception;

    void test015fetchFollowerTest() throws Exception;

    void test016fetchFriendsTest() throws Exception;

    void test017removeOldDataFromStorageTest() throws Exception;

    void test018getHomeTimeline() throws Exception;

    void test019getUserTimeline() throws Exception;

    void test020getMentions() throws Exception;

    void test021getFavorites() throws Exception;

    void test022getRetweetsOfMe() throws Exception;

    void test023getLists() throws Exception;
}
