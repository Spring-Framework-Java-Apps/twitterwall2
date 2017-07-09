package org.woehlke.twitterwall.oodm.listener;

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
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(Tweet domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(Tweet domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(Tweet domainObject) {
    log.debug("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(Tweet domainObject) {
    log.debug("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(Tweet domainObject) {
    log.debug("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(Tweet domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }
}
