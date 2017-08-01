package org.woehlke.twitterwall.scheduled.mq.endoint;

/**
 * @see org.woehlke.twitterwall.scheduled.mq.endoint.StartTask
 * @see org.woehlke.twitterwall.scheduled.mq.endoint.AsyncStartTask
 */
public interface StartTaskTest extends AsyncStartTaskTest {

    void createImprintUserTest() throws Exception;

    void createTestDataForUserTest() throws Exception;

    void createTestDataForTweetsTest() throws Exception;
}
