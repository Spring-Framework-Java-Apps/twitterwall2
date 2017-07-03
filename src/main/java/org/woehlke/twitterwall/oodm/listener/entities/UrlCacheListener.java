package org.woehlke.twitterwall.oodm.listener.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class UrlCacheListener {

  private static final Logger log = LoggerFactory.getLogger(UrlCacheListener.class);

  @PrePersist
  public void onPrePersist(UrlCache domainObject) {
    log.info("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(UrlCache domainObject) {
    log.info("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(UrlCache domainObject) {
    log.info("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(UrlCache domainObject) {
    log.info("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(UrlCache domainObject) {
    log.info("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(UrlCache domainObject) {
    log.info("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(UrlCache domainObject) {
    log.info("loaded: "+domainObject.toString());
  }
}
