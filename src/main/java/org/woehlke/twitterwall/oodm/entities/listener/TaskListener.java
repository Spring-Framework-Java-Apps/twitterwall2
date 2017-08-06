package org.woehlke.twitterwall.oodm.entities.listener;

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
        log.debug("try to Persist: "+domainObject.getUniqueId());
    }

    @PreUpdate
    public void onPreUpdate(Task domainObject) {
        log.debug("try to Update: "+domainObject.getUniqueId());
    }

    @PreRemove
    public void onPreRemove(Task domainObject) {
        log.debug("try to Remove: "+domainObject.getUniqueId());
    }

    @PostPersist
    public void onPostPersist(Task domainObject) {
        log.debug("Persisted: "+domainObject.getUniqueId());
    }

    @PostUpdate
    public void onPostUpdate(Task domainObject) {
        log.debug("Updated: "+domainObject.getUniqueId());
    }

    @PostRemove
    public void onPostRemove(Task domainObject) {
        log.debug("Removed: "+domainObject.getUniqueId());
    }

    @PostLoad
    public void onPostLoad(Task domainObject) {
        log.debug("loaded: "+domainObject.getUniqueId());
    }
}
