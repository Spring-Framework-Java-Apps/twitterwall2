package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolService extends DomainService<TickerSymbol> {
    
    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);

    TickerSymbol storeTickerSymbol(TickerSymbol tickerSymbol);
    
}
