package org.woehlke.twitterwall.oodm.listener.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class HashTagListener {

  private static final Logger log = LoggerFactory.getLogger(HashTagListener.class);

  @PrePersist
  public void onPrePersist(HashTag domainObject) {
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(HashTag domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(HashTag domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(HashTag domainObject) {
    log.debug("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(HashTag domainObject) {
    log.debug("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(HashTag domainObject) {
    log.debug("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(HashTag domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }


}
