package org.woehlke.twitterwall.oodm.model.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.model.Media;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class MediaListener {

  private static final Logger log = LoggerFactory.getLogger(MediaListener.class);

  @PrePersist
  public void onPrePersist(Media domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
    log.trace("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(Media domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
    log.trace("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(Media domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
    log.trace("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(Media domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
    log.trace("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(Media domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
    log.trace("Updated: "+domainObject.getUniqueId());
  }

  @PostRemove
  public void onPostRemove(Media domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
    log.trace("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(Media domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
    log.trace("loaded: "+domainObject.toString());
  }
}
