package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.TickerSymbolTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 28.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TickerSymbolTransformServiceImpl implements TickerSymbolTransformService {

    @Override
    public TickerSymbol transform(TickerSymbolEntity tickerSymbol) {
        String tickerSymbolString = tickerSymbol.getTickerSymbol();
        String url = tickerSymbol.getUrl();
        int[] indices = tickerSymbol.getIndices();
        TickerSymbol myTickerSymbolEntity = new TickerSymbol(tickerSymbolString, url, indices);
        return myTickerSymbolEntity;
    }

    private Set<TickerSymbol> getTickerSymbolsFor(String description) {
        Set<TickerSymbol> tickerSymbols = new LinkedHashSet<TickerSymbol>();
        if (description != null) {
            Pattern urlPattern = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")(" + Entities.stopChar + ")");
            Matcher m3 = urlPattern.matcher(description);
            while (m3.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m3.group(1)));
            }
            Pattern urlPattern2 = Pattern.compile("("+Url.URL_PATTTERN_FOR_USER+")$");
            Matcher m4 = urlPattern2.matcher(description);
            while (m4.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m4.group(1)));
            }
        }
        return tickerSymbols;
    }

    @Override
    public Set<TickerSymbol> getTickerSymbolsFor(TwitterProfile userSource) {
        Set<TickerSymbol> tickerSymbolsTarget = new LinkedHashSet<TickerSymbol>();
        //TODO: bla
        String description = userSource.getDescription();
        //Set<TickerSymbol> tickerSymbolsTarget = getTickerSymbolsFor(description);
        return tickerSymbolsTarget;
    }
}
