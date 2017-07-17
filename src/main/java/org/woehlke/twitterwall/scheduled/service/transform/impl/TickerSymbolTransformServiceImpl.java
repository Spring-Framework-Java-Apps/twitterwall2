package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.parts.EntitiesFilter;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.scheduled.service.transform.TickerSymbolTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TickerSymbolTransformServiceImpl extends EntitiesFilter implements TickerSymbolTransformService {

    @Override
    public TickerSymbol transform(TickerSymbolEntity tickerSymbol) {
        String tickerSymbolString = tickerSymbol.getTickerSymbol();
        String url = tickerSymbol.getUrl();
        TickerSymbol myTickerSymbolEntity = new TickerSymbol(tickerSymbolString, url);
        return myTickerSymbolEntity;
    }

    @Override
    public Set<TickerSymbol> getTickerSymbolsFor(TwitterProfile userSource) {
        return new LinkedHashSet<TickerSymbol>();
    }
}
