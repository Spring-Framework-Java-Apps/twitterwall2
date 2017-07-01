package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository                                                                          
public class TickerSymbolRepositoryImpl extends DomainRepositoryImpl<TickerSymbol> implements TickerSymbolRepository {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public TickerSymbol findByUrl(String url) {
        String name = "TickerSymbols.findByUrl";
        try {
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("url", url);
            TickerSymbol result = query.getSingleResult();
            log.info(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.info(name+" not found: " + url);
            throw e;
        }
    }

    @Override
    public TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url) {
        String name = "TickerSymbols.findByTickerSymbolAndUrl";
        try {
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("url", url);
            query.setParameter("tickerSymbol", tickerSymbol);
            TickerSymbol result = query.getSingleResult();
            log.info(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.info(name+" not found: " + url);
            throw e;
        }
    }
}
