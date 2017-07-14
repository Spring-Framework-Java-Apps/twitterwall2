package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Entities;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.scheduled.service.transform.entities.TickerSymbolTransformService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTP;
import static org.woehlke.twitterwall.oodm.entities.entities.Url.URL_PATTTERN_FOR_USER_HTTPS;

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

            Pattern myUrl1 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m1 = myUrl1.matcher(description);
            while (m1.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m1.group(2)));
            }
            Pattern myUrl2 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTPS + ")(" + Entities.stopChar + ")");
            Matcher m2 = myUrl2.matcher(description);
            while (m2.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m2.group(1)));
            }
            Pattern myUrl3 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTPS + ")$");
            Matcher m3 = myUrl3.matcher(description);
            while (m3.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m3.group(2)));
            }
            Pattern myUrl11 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m11 = myUrl11.matcher(description);
            while (m11.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m11.group(2)));
            }
            Pattern myUrl12 = Pattern.compile("^(" + URL_PATTTERN_FOR_USER_HTTP + ")(" + Entities.stopChar + ")");
            Matcher m12 = myUrl12.matcher(description);
            while (m12.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m12.group(1)));
            }
            Pattern myUrl13 = Pattern.compile("(" + Entities.stopChar + ")(" + URL_PATTTERN_FOR_USER_HTTP + ")$");
            Matcher m13 = myUrl13.matcher(description);
            while (m13.find()) {
                tickerSymbols.add(TickerSymbol.getTickerSymbolFactory(m13.group(2)));
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
