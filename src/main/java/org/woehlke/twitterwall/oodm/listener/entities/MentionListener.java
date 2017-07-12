package org.woehlke.twitterwall.oodm.listener.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class MentionListener {

  private static final Logger log = LoggerFactory.getLogger(MentionListener.class);

  @PrePersist
  public void onPrePersist(Mention domainObject) {
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(Mention domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(Mention domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(Mention domainObject) {
    log.info("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(Mention domainObject) {
    log.info("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(Mention domainObject) {
    log.info("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(Mention domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }
}
