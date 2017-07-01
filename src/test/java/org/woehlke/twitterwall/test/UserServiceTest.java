package org.woehlke.twitterwall.test;

import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 01.07.17.
 */
public interface UserServiceTest {

    void createTestData();

    User createImprintUser();

    User createUser(String username);
}
