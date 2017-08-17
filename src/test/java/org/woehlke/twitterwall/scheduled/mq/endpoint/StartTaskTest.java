package org.woehlke.twitterwall.scheduled.mq.endpoint;

/**
 * @see org.woehlke.twitterwall.scheduled.mq.endpoint.StartTask
 * @see org.woehlke.twitterwall.scheduled.mq.endpoint.AsyncStartTask
 */
public interface StartTaskTest extends AsyncStartTaskTest {

    void createImprintUserTest() throws Exception;

    void createTestDataUsersTest() throws Exception;

    void createTestDataTweetsTest() throws Exception;

}
