package org.woehlke.twitterwall.oodm.model.tasks;

/**
 * Created by tw on 10.07.17.
 */
public enum TaskStatus {

    READY,
    RUNNING,
    FINISHED,
    ERROR,
    FINAL_ERROR,
    WARN,
    STOPPED_BY_TIMEOUT,
    NULL
}
