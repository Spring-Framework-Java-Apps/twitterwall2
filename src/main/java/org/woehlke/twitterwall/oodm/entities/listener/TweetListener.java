package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class TweetListener  {

  private static final Logger log = LoggerFactory.getLogger(TweetListener.class);

  @PrePersist
  public void onPrePersist(Tweet domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
  }

  @PreUpdate
  public void onPreUpdate(Tweet domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
  }

  @PreRemove
  public void onPreRemove(Tweet domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
  }

  @PostPersist
  public void onPostPersist(Tweet domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
  }

  @PostUpdate
  public void onPostUpdate(Tweet domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
  }

  @PostRemove
  public void onPostRemove(Tweet domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
  }

  @PostLoad
  public void onPostLoad(Tweet domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
  }
}
