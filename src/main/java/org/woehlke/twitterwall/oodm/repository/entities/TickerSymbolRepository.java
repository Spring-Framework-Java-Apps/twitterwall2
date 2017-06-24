package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repository.common.OodmRepository;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolRepository extends OodmRepository {

    TickerSymbol persist(TickerSymbol myTickerSymbolEntity);

    TickerSymbol merge(TickerSymbol tickerSymbol);

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);
}
