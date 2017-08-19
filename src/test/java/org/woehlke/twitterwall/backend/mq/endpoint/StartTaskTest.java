package org.woehlke.twitterwall.backend.mq.endpoint;

import org.woehlke.twitterwall.backend.mq.endpoint.tasks.StartTask;

/**
 * @see StartTask
 * @see StartTaskAsynchron
 */
public interface StartTaskTest extends AsyncStartTaskTest {

    void createImprintUserTest() throws Exception;

    void createTestDataUsersTest() throws Exception;

    void createTestDataTweetsTest() throws Exception;

}
