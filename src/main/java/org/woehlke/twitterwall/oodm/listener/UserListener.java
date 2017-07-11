package org.woehlke.twitterwall.oodm.listener;

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
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(User domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(User domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(User domainObject) {
    log.info("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(User domainObject) {
    log.info("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(User domainObject) {
    log.info("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(User domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }
}
