package org.woehlke.twitterwall.oodm.service.entities.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repository.entities.TickerSymbolRepository;
import org.woehlke.twitterwall.oodm.service.entities.TickerSymbolService;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TickerSymbolServiceImpl implements TickerSymbolService {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolServiceImpl.class);

    private final TickerSymbolRepository tickerSymbolRepository;

    @Autowired
    public TickerSymbolServiceImpl(TickerSymbolRepository tickerSymbolRepository) {
        this.tickerSymbolRepository = tickerSymbolRepository;
    }

    @Override
    public TickerSymbol create(TickerSymbol domainObject, Task task) {
        domainObject.setCreatedBy(task);
        return this.tickerSymbolRepository.persist(domainObject);
    }

    @Override
    public TickerSymbol update(TickerSymbol tickerSymbol, Task task) {
        tickerSymbol.setUpdatedBy(task);
        return this.tickerSymbolRepository.update(tickerSymbol);
    }

    @Override
    public List<TickerSymbol> getAll() {
        return this.tickerSymbolRepository.getAll(TickerSymbol.class);
    }

    @Override
    public long count() {
        return this.tickerSymbolRepository.count(TickerSymbol.class);
    }

    @Override
    public TickerSymbol store(TickerSymbol tickerSymbol, Task task) {
        String msg = "TickerSymbol.storeTickerSymbol: ";
        try{
            log.debug(msg+"try to find: "+tickerSymbol.toString());
            TickerSymbol tickerSymbolPers = this.tickerSymbolRepository.findByTickerSymbolAndUrl(tickerSymbol.getTickerSymbol(), tickerSymbol.getUrl());
            log.debug(msg+"found: "+tickerSymbolPers.toString());
            tickerSymbol.setId(tickerSymbolPers.getId());
            tickerSymbol.setCreatedBy(tickerSymbolPers.getCreatedBy());
            tickerSymbol.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+tickerSymbol.toString());
            return this.tickerSymbolRepository.update(tickerSymbol);
        } catch (EmptyResultDataAccessException e) {
            tickerSymbol.setCreatedBy(task);
            log.debug(msg+"not found and try to persist: "+tickerSymbol.toString());
            return this.tickerSymbolRepository.persist(tickerSymbol);
        }
    }

    @Override
    public TickerSymbol findByUrl(String url) {
        return this.tickerSymbolRepository.findByUrl(url);
    }
}
