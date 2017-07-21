package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.UrlCache;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class UrlCacheListener {

  private static final Logger log = LoggerFactory.getLogger(UrlCacheListener.class);

  @PrePersist
  public void onPrePersist(UrlCache domainObject) {
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(UrlCache domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(UrlCache domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(UrlCache domainObject) {
    log.debug("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(UrlCache domainObject) {
    log.debug("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(UrlCache domainObject) {
    log.debug("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(UrlCache domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }
}
