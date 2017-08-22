package org.woehlke.twitterwall.oodm.model.tasks;

public enum TaskSendType {

    NULL,
    NO_MQ,
    SEND_AND_WAIT_FOR_RESULT,
    FIRE_AND_FORGET
}
