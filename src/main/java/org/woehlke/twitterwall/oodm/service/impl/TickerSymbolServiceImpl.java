package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.dao.TickerSymbolDao;
import org.woehlke.twitterwall.oodm.repositories.TickerSymbolRepository;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;


/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TickerSymbolServiceImpl implements TickerSymbolService {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolServiceImpl.class);

    private final TickerSymbolDao tickerSymbolDao;

    private final TickerSymbolRepository tickerSymbolRepository;

    @Autowired
    public TickerSymbolServiceImpl(TickerSymbolDao tickerSymbolDao, TickerSymbolRepository tickerSymbolRepository) {
        this.tickerSymbolDao = tickerSymbolDao;
        this.tickerSymbolRepository = tickerSymbolRepository;
    }

    @Override
    public TickerSymbol create(TickerSymbol domainObject, Task task) {
        domainObject.setCreatedBy(task);
        return tickerSymbolRepository.save(domainObject);
        //return this.tickerSymbolDao.persist(domainObject);
    }

    @Override
    public TickerSymbol update(TickerSymbol tickerSymbol, Task task) {
        tickerSymbol.setUpdatedBy(task);
        return tickerSymbolRepository.save(tickerSymbol);
        //return this.tickerSymbolDao.update(tickerSymbol);
    }

    @Override
    public Page<TickerSymbol> getAll(Pageable pageRequest) {
        return tickerSymbolRepository.findAll(pageRequest);
        //return this.tickerSymbolDao.getAll(TickerSymbol.class,pageRequest);
    }

    @Override
    public long count() {
        return tickerSymbolRepository.count();
        //return this.tickerSymbolDao.count(TickerSymbol.class);
    }

    @Override
    public TickerSymbol store(TickerSymbol tickerSymbol, Task task) {
        String msg = "TickerSymbol.storeTickerSymbol: ";
        try{
            log.debug(msg+"try to find: "+tickerSymbol.toString());
            TickerSymbol tickerSymbolPers = this.tickerSymbolDao.findByTickerSymbolAndUrl(tickerSymbol.getTickerSymbol(), tickerSymbol.getUrl());
            log.debug(msg+"found: "+tickerSymbolPers.toString());
            tickerSymbol.setId(tickerSymbolPers.getId());
            tickerSymbol.setCreatedBy(tickerSymbolPers.getCreatedBy());
            tickerSymbol.setUpdatedBy(task);
            log.debug(msg+"found and try to update: "+tickerSymbol.toString());
            return this.tickerSymbolDao.update(tickerSymbol);
        } catch (EmptyResultDataAccessException e) {
            tickerSymbol.setCreatedBy(task);
            log.debug(msg+"not found and try to persist: "+tickerSymbol.toString());
            return this.tickerSymbolDao.persist(tickerSymbol);
        }
    }

    @Override
    public TickerSymbol findByUrl(String url) {
        return this.tickerSymbolDao.findByUrl(url);
    }
}
