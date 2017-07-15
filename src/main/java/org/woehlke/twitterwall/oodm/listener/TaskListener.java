package org.woehlke.twitterwall.oodm.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.Task;

import javax.persistence.*;

/**
 * Created by tw on 09.07.17.
 */
public class TaskListener {

    private static final Logger log = LoggerFactory.getLogger(TaskListener.class);

    @PrePersist
    public void onPrePersist(Task domainObject) {
        log.debug("try to Persist: "+domainObject.toString());
    }

    @PreUpdate
    public void onPreUpdate(Task domainObject) {
        log.debug("try to Update: "+domainObject.toString());
    }

    @PreRemove
    public void onPreRemove(Task domainObject) {
        log.debug("try to Remove: "+domainObject.toString());
    }

    @PostPersist
    public void onPostPersist(Task domainObject) {
        log.info("Persisted: "+domainObject.toString());
    }

    @PostUpdate
    public void onPostUpdate(Task domainObject) {
        log.info("Updated: "+domainObject.toString());
    }

    @PostRemove
    public void onPostRemove(Task domainObject) {
        log.info("Removed: "+domainObject.toString());
    }

    @PostLoad
    public void onPostLoad(Task domainObject) {
        log.debug("loaded: "+domainObject.toString());
    }
}
