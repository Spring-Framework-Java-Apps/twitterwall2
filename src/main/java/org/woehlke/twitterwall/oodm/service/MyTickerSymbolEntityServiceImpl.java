package org.woehlke.twitterwall.oodm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.oodm.entities.MyTickerSymbolEntity;
import org.woehlke.twitterwall.oodm.repository.MyTickerSymbolEntityRepository;

/**
 * Created by tw on 12.06.17.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = true)
public class MyTickerSymbolEntityServiceImpl implements MyTickerSymbolEntityService {

    private final MyTickerSymbolEntityRepository myTickerSymbolEntityRepository;

    @Autowired
    public MyTickerSymbolEntityServiceImpl(MyTickerSymbolEntityRepository myTickerSymbolEntityRepository) {
        this.myTickerSymbolEntityRepository = myTickerSymbolEntityRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly = false)
    public MyTickerSymbolEntity store(MyTickerSymbolEntity tickerSymbol) {
        return this.myTickerSymbolEntityRepository.persist(tickerSymbol);
    }
}
