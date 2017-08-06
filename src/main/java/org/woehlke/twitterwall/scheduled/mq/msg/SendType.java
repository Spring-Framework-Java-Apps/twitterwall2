package org.woehlke.twitterwall.scheduled.mq.msg;

public enum SendType {

    NULL,
    NO_MQ,
    SEND_AND_WAIT_FOR_RESULT,
    FIRE_AND_FORGET
}
