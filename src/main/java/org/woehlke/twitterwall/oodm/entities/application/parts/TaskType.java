package org.woehlke.twitterwall.oodm.entities.application.parts;

/**
 * Created by tw on 09.07.17.
 */
public enum TaskType {

    FETCH_TWEETS_FROM_TWITTER_SEARCH,
    UPDATE_TWEETS,
    UPDATE_USER_PROFILES,
    UPDATE_USER_PROFILES_FROM_MENTIONS,
    FETCH_USERS_FROM_DEFINED_USER_LIST,
    CONTROLLER_GET_TESTDATA_TWEETS,
    CONTROLLER_GET_TESTDATA_USER,
    CONTROLLER_ADD_USER_FOR_SCREEN_NAME;
}
