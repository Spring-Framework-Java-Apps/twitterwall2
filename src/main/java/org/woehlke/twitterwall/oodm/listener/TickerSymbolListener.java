package org.woehlke.twitterwall.oodm.listener;

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
    log.debug("try to Persist: "+domainObject.toString());
  }

  @PreUpdate
  public void onPreUpdate(TickerSymbol domainObject) {
    log.debug("try to Update: "+domainObject.toString());
  }

  @PreRemove
  public void onPreRemove(TickerSymbol domainObject) {
    log.debug("try to Remove: "+domainObject.toString());
  }

  @PostPersist
  public void onPostPersist(TickerSymbol domainObject) {
    log.info("Persisted: "+domainObject.toString());
  }

  @PostUpdate
  public void onPostUpdate(TickerSymbol domainObject) {
    log.info("Updated: "+domainObject.toString());
  }

  @PostRemove
  public void onPostRemove(TickerSymbol domainObject) {
    log.info("Removed: "+domainObject.toString());
  }

  @PostLoad
  public void onPostLoad(TickerSymbol domainObject) {
    log.debug("loaded: "+domainObject.toString());
  }
}
