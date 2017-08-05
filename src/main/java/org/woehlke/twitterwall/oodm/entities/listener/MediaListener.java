package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.Media;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class MediaListener {

  private static final Logger log = LoggerFactory.getLogger(MediaListener.class);

  @PrePersist
  public void onPrePersist(Media domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
  }

  @PreUpdate
  public void onPreUpdate(Media domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
  }

  @PreRemove
  public void onPreRemove(Media domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
  }

  @PostPersist
  public void onPostPersist(Media domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
  }

  @PostUpdate
  public void onPostUpdate(Media domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
  }

  @PostRemove
  public void onPostRemove(Media domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
  }

  @PostLoad
  public void onPostLoad(Media domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
  }
}
