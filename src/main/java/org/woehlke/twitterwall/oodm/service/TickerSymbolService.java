package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithUrl;


/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolService extends DomainServiceWithUrl<TickerSymbol>,DomainServiceWithTask<TickerSymbol> {

}