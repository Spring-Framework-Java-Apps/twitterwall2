package org.woehlke.twitterwall.frontend.controller.common;


import org.woehlke.twitterwall.oodm.entities.User;

@Deprecated
public interface PrepareDataTest {

    @Deprecated
    void getTestDataTweets(String msg);

    @Deprecated
    void getTestDataUser(String msg);

    @Deprecated
    User createUser(String screenName);
}
