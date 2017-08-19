package org.woehlke.twitterwall.scheduled.service.transform.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.entities.EntitiesFilter;
import org.woehlke.twitterwall.oodm.model.TickerSymbol;
import org.woehlke.twitterwall.scheduled.service.transform.TickerSymbolTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */

@Component
public class TickerSymbolTransformServiceImpl extends EntitiesFilter implements TickerSymbolTransformService {

    @Override
    public TickerSymbol transform(TickerSymbolEntity tickerSymbol,Task task) {
        String tickerSymbolString = tickerSymbol.getTickerSymbol();
        String url = tickerSymbol.getUrl();
        TickerSymbol myTickerSymbolEntity = new TickerSymbol(task, null, tickerSymbolString, url);
        return myTickerSymbolEntity;
    }

    @Override
    public Set<TickerSymbol> getTickerSymbolsFor(TwitterProfile userSource,Task task) {
        return new LinkedHashSet<TickerSymbol>();
    }
}
