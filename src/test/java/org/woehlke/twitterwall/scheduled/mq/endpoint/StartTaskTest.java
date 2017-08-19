package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.woehlke.twitterwall.scheduled.mq.endpoint.tasks.StartTask;

/**
 * @see StartTask
 * @see StartTaskAsynchron
 */
public interface StartTaskTest extends AsyncStartTaskTest {

    void createImprintUserTest() throws Exception;

    void createTestDataUsersTest() throws Exception;

    void createTestDataTweetsTest() throws Exception;

}
