package org.woehlke.twitterwall.backend.service.transform.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.entities.EntitiesFilter;
import org.woehlke.twitterwall.oodm.model.TickerSymbol;
import org.woehlke.twitterwall.backend.service.transform.TickerSymbolTransformService;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by tw on 28.06.17.
 */

@Component
public class TickerSymbolTransformServiceImpl extends EntitiesFilter implements TickerSymbolTransformService {

    @Override
    public TickerSymbol transform(TickerSymbolEntity tickerSymbolSource,Task createdBy) {
        String tickerSymbolString = tickerSymbolSource.getTickerSymbol();
        String url = tickerSymbolSource.getUrl();
        Task updatedBy = null;
        TickerSymbol tickerSymboTarget = new TickerSymbol(createdBy, updatedBy, tickerSymbolString, url);
        return tickerSymboTarget;
    }

    @Override
    public Set<TickerSymbol> getTickerSymbolsFor(TwitterProfile userSource,Task task) {
        return new LinkedHashSet<TickerSymbol>();
    }
}
