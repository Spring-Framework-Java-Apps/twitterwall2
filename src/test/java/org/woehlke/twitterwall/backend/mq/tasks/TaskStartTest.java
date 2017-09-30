package org.woehlke.twitterwall.backend.mq.tasks;

/**
 * @see TaskStart
 * @see TaskStartFireAndForget
 */
public interface TaskStartTest extends TaskStartFireAndForgetTest {

    void test100createImprintUserTest() throws Exception;

    void test110createTestDataUsersTest() throws Exception;

    void test120createTestDataTweetsTest() throws Exception;

}
