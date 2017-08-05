package org.woehlke.twitterwall.frontend.controller.common;


import org.woehlke.twitterwall.oodm.entities.User;

public interface PrepareDataTest {

    void getTestDataTweets(String msg);

    void getTestDataUser(String msg);

    User createUser(String screenName);
}
