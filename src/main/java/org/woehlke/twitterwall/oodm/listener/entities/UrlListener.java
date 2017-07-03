package org.woehlke.twitterwall.oodm.listener.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.entities.Url;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class UrlListener {

  private static final Logger log = LoggerFactory.getLogger(UrlListener.class);

  @PrePersist
  public void onPrePersist(Url domainObject) {
    log.info("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(Url domainObject) {
    log.info("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(Url domainObject) {
    log.info("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(Url domainObject) {
    log.info("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(Url domainObject) {
    log.info("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(Url domainObject) {
    log.info("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(Url domainObject) {
    log.info("loaded: "+domainObject.toString());
  }
}
