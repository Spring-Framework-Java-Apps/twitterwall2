package org.woehlke.twitterwall.oodm.repository.entities;

import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithUrl;

/**
 * Created by tw on 12.06.17.
 */
public interface TickerSymbolRepository extends DomainRepositoryWithUrl<TickerSymbol> {

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);
}
