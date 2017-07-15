package org.woehlke.twitterwall.oodm.dao;

import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.dao.parts.DomainDaoWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolDao extends DomainDaoWithUrl<TickerSymbol> {
    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);
}
