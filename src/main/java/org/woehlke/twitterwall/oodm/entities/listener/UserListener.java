package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.User;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class UserListener {

  private static final Logger log = LoggerFactory.getLogger(UserListener.class);

  @PrePersist
  public void onPrePersist(User domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
  }

  @PreUpdate
  public void onPreUpdate(User domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
  }

  @PreRemove
  public void onPreRemove(User domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
  }

  @PostPersist
  public void onPostPersist(User domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
  }

  @PostUpdate
  public void onPostUpdate(User domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
  }

  @PostRemove
  public void onPostRemove(User domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
  }

  @PostLoad
  public void onPostLoad(User domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
  }
}
