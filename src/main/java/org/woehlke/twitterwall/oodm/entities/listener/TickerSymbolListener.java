package org.woehlke.twitterwall.oodm.entities.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;

import javax.persistence.*;

/**
 * Created by tw on 03.07.17.
 */
public class TickerSymbolListener {

  private static final Logger log = LoggerFactory.getLogger(TickerSymbolListener.class);

  @PrePersist
  public void onPrePersist(TickerSymbol domainObject) {
    log.debug("try to Persist: "+domainObject.getUniqueId());
  }

  @PreUpdate
  public void onPreUpdate(TickerSymbol domainObject) {
    log.debug("try to Update: "+domainObject.getUniqueId());
  }

  @PreRemove
  public void onPreRemove(TickerSymbol domainObject) {
    log.debug("try to Remove: "+domainObject.getUniqueId());
  }

  @PostPersist
  public void onPostPersist(TickerSymbol domainObject) {
    log.debug("Persisted: "+domainObject.getUniqueId());
  }

  @PostUpdate
  public void onPostUpdate(TickerSymbol domainObject) {
    log.debug("Updated: "+domainObject.getUniqueId());
  }

  @PostRemove
  public void onPostRemove(TickerSymbol domainObject) {
    log.debug("Removed: "+domainObject.getUniqueId());
  }

  @PostLoad
  public void onPostLoad(TickerSymbol domainObject) {
    log.debug("loaded: "+domainObject.getUniqueId());
  }
}
