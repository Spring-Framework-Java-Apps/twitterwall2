package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.TickerSymbolRepository;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;


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
        return tickerSymbolRepository.save(domainObject);
    }

    @Override
    public TickerSymbol update(TickerSymbol tickerSymbol, Task task) {
        tickerSymbol.setUpdatedBy(task);
        return tickerSymbolRepository.save(tickerSymbol);
    }

    @Override
    public Page<TickerSymbol> getAll(Pageable pageRequest) {
        return tickerSymbolRepository.findAll(pageRequest);
    }

    @Override
    public long count() {
        return tickerSymbolRepository.count();
    }

    @Override
    public TickerSymbol store(TickerSymbol tickerSymbol, Task task) {
        String msg = "TickerSymbol.storeTickerSymbol: ";
        log.debug(msg+"try to find: "+tickerSymbol.toString());
        TickerSymbol tickerSymbolPers = tickerSymbolRepository.findByTickerSymbolAndUrl(tickerSymbol.getTickerSymbol(), tickerSymbol.getUrl());
        if(tickerSymbolPers!=null){
            log.debug(msg+"found: "+tickerSymbolPers.toString());
            tickerSymbol.setId(tickerSymbolPers.getId());
            tickerSymbol.setCreatedBy(tickerSymbolPers.getCreatedBy());
            tickerSymbol.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+tickerSymbol.toString());
            return tickerSymbolRepository.save(tickerSymbol);
        }else{
            tickerSymbol.setCreatedBy(task);
            log.debug(msg+"not found and try to persist: "+tickerSymbol.toString());
            return tickerSymbolRepository.save(tickerSymbol);
        }
    }

    @Override
    public TickerSymbol findByUrl(String url) {
        return tickerSymbolRepository.findByUrl(url);
    }
}
