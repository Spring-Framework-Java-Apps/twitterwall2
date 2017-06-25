package org.woehlke.twitterwall.oodm.service.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindTickerSymbolByTickerSymbolAndUrlException;
import org.woehlke.twitterwall.oodm.repository.entities.TickerSymbolRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TickerSymbolServiceImpl implements TickerSymbolService {

    private final TickerSymbolRepository tickerSymbolRepository;

    @Autowired
    public TickerSymbolServiceImpl(TickerSymbolRepository tickerSymbolRepository) {
        this.tickerSymbolRepository = tickerSymbolRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public TickerSymbol store(TickerSymbol tickerSymbol) {
        return this.tickerSymbolRepository.persist(tickerSymbol);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public TickerSymbol update(TickerSymbol tickerSymbol) {
        return this.tickerSymbolRepository.merge(tickerSymbol);
    }

    @Override
    public TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url) {
        return this.tickerSymbolRepository.findByTickerSymbolAndUrl(tickerSymbol, url);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public TickerSymbol storeTickerSymbol(TickerSymbol tickerSymbol) {
        try {
            TickerSymbol tickerSymbolPers = this.tickerSymbolRepository.findByTickerSymbolAndUrl(tickerSymbol.getTickerSymbol(), tickerSymbol.getUrl());
            tickerSymbolPers.setUrl(tickerSymbol.getUrl());
            tickerSymbolPers.setIndices(tickerSymbol.getIndices());
            tickerSymbolPers.setTickerSymbol(tickerSymbol.getTickerSymbol());
            return this.tickerSymbolRepository.merge(tickerSymbolPers);

        } catch (FindTickerSymbolByTickerSymbolAndUrlException e) {
            return this.tickerSymbolRepository.persist(tickerSymbol);
        }
    }

    @Override
    public Set<TickerSymbol> transformTwitterEntitiesTickerSymbols(List<TickerSymbolEntity> tickerSymbols) {
        Set<TickerSymbol> myTickerSymbolEntities = new LinkedHashSet<TickerSymbol>();
        for (TickerSymbolEntity tickerSymbol : tickerSymbols) {
            String tickerSymbolString = tickerSymbol.getTickerSymbol();
            String url = tickerSymbol.getUrl();
            int[] indices = tickerSymbol.getIndices();
            TickerSymbol myTickerSymbolEntity = new TickerSymbol(tickerSymbolString, url, indices);
            myTickerSymbolEntities.add(myTickerSymbolEntity);
        }
        return myTickerSymbolEntities;
    }
}
