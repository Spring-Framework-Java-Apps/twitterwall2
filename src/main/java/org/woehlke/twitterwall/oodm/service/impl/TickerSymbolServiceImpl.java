package org.woehlke.twitterwall.oodm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.TaskRepository;
import org.woehlke.twitterwall.oodm.repositories.TickerSymbolRepository;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;


/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TickerSymbolServiceImpl extends DomainServiceWithTaskImpl<TickerSymbol> implements TickerSymbolService {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolServiceImpl.class);

    private final TickerSymbolRepository tickerSymbolRepository;

    @Autowired
    public TickerSymbolServiceImpl(TickerSymbolRepository tickerSymbolRepository, TaskRepository taskRepository) {
        super(tickerSymbolRepository,taskRepository);
        this.tickerSymbolRepository = tickerSymbolRepository;
    }

    @Override
    public TickerSymbol findByUrl(String url) {
        return tickerSymbolRepository.findByUrl(url);
    }

}
