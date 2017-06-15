package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolRepository {

    TickerSymbol persist(TickerSymbol myTickerSymbolEntity);

    TickerSymbol merge(TickerSymbol tickerSymbol);

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);
}
