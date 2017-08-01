package org.woehlke.twitterwall.scheduled.mq.endpoint;


/**
 * @see org.woehlke.twitterwall.scheduled.mq.endpoint.AsyncStartTask
 */
public interface AsyncStartTaskTest {

    void updateTweetsTest() throws Exception;

    void updateUserProfilesTest() throws Exception;

    void updateUserProfilesFromMentionsTest() throws Exception;

    void fetchTweetsFromTwitterSearchTest() throws Exception;

    void fetchUsersFromDefinedUserListTest() throws Exception;
}
