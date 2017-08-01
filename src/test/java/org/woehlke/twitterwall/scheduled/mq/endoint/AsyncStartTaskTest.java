package org.woehlke.twitterwall.scheduled.mq.endoint;


/**
 * @see org.woehlke.twitterwall.scheduled.mq.endoint.AsyncStartTask
 */
public interface AsyncStartTaskTest {

    void updateTweetsTest() throws Exception;

    void updateUserProfilesTest() throws Exception;

    void updateUserProfilesFromMentionsTest() throws Exception;

    void fetchTweetsFromTwitterSearchTest() throws Exception;

    void fetchUsersFromDefinedUserListTest() throws Exception;
}
