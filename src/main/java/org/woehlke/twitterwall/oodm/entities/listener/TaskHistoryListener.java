package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;

import javax.persistence.*;

/**
 * Created by tw on 10.07.17.
 */
public class TaskHistoryListener {

    private static final Logger log = LoggerFactory.getLogger(TaskHistoryListener.class);

    @PrePersist
    public void onPrePersist(TaskHistory domainObject) {
        log.debug("try to Persist: "+domainObject.getUniqueId());
    }

    @PreUpdate
    public void onPreUpdate(TaskHistory domainObject) {
        log.debug("try to Update: "+domainObject.getUniqueId());
    }

    @PreRemove
    public void onPreRemove(TaskHistory domainObject) {
        log.debug("try to Remove: "+domainObject.getUniqueId());
    }

    @PostPersist
    public void onPostPersist(TaskHistory domainObject) {
        log.debug("Persisted: "+domainObject.getUniqueId());
    }

    @PostUpdate
    public void onPostUpdate(TaskHistory domainObject) {
        log.debug("Updated: "+domainObject.getUniqueId());
    }

    @PostRemove
    public void onPostRemove(TaskHistory domainObject) {
        log.debug("Removed: "+domainObject.getUniqueId());
    }

    @PostLoad
    public void onPostLoad(TaskHistory domainObject) {
        log.debug("loaded: "+domainObject.getUniqueId());
    }
}
