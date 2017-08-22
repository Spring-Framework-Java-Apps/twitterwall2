package org.woehlke.twitterwall.oodm.model.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.model.Mention;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class MentionListener {

  private static final Logger log = LoggerFactory.getLogger(MentionListener.class);

  @PrePersist
  public void onPrePersist(Mention domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
    log.trace("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(Mention domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
    log.trace("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(Mention domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
    log.trace("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(Mention domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
    log.trace("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(Mention domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
    log.trace("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(Mention domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
    log.trace("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(Mention domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
    log.trace("loaded: "+domainObject.toString());
  }
}
