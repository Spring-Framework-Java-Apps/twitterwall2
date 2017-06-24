package org.woehlke.twitterwall.oodm.service.entities;

import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolService extends OodmService {
    TickerSymbol store(TickerSymbol tickerSymbol);

    TickerSymbol update(TickerSymbol tickerSymbol);

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);
}
