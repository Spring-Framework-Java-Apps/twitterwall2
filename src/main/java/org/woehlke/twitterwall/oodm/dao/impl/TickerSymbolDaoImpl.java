package org.woehlke.twitterwall.oodm.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.dao.parts.impl.DomainDaoImpl;
import org.woehlke.twitterwall.oodm.dao.TickerSymbolDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class TickerSymbolDaoImpl extends DomainDaoImpl<TickerSymbol> implements TickerSymbolDao {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TickerSymbol findByUrl(String url) {
        String name = "TickerSymbols.findByUrl";
        log.debug(name);
        try {
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("url", url);
            TickerSymbol result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + url);
            throw e;
        }
    }

    @Override
    public TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url) {
        String name = "TickerSymbols.findByTickerSymbolAndUrl";
        log.debug(name);
        try {
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("url", url);
            query.setParameter("tickerSymbol", tickerSymbol);
            TickerSymbol result = query.getSingleResult();
            log.debug(name+" found: " + result.toString());
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug(name+" not found: " + url);
            throw e;
        }
    }
}
