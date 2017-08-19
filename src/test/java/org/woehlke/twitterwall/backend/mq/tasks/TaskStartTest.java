package org.woehlke.twitterwall.backend.mq.tasks;

/**
 * @see TaskStart
 * @see StartTaskAsynchron
 */
public interface TaskStartTest extends TaskStartFireAndForgetTest {

    void createImprintUserTest() throws Exception;

    void createTestDataUsersTest() throws Exception;

    void createTestDataTweetsTest() throws Exception;

}
