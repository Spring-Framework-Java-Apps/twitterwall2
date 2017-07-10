package org.woehlke.twitterwall.scheduled.service.transform.entities.impl;

import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.scheduled.service.transform.entities.TickerSymbolTransformService;

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
}
