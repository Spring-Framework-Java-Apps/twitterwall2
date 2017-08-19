package org.woehlke.twitterwall.oodm.entities.parts;

public enum MentionStatus {

    NULL,
    FETCHED_FROM_TWITTER,
    CREATED_BY_TEXT,
    HAS_USER__FETCHED_FROM_TWITTER,
    HAS_USER__CREATED_BY_TEXT,
    NO_USER_FOUND__FETCHED_FROM_TWITTER,
    NO_USER_FOUND__CREATED_BY_TEXT

}
