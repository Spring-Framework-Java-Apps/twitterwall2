package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolService extends OodmService {

    TickerSymbol store(TickerSymbol tickerSymbol);

    TickerSymbol update(TickerSymbol tickerSymbol);

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);

    TickerSymbol storeTickerSymbol(TickerSymbol tickerSymbol);

    Set<TickerSymbol> transformTwitterEntitiesTickerSymbols(List<TickerSymbolEntity> tickerSymbols);
}
