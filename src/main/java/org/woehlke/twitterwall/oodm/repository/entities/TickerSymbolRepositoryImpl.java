package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindTickerSymbolByTickerSymbolAndUrlException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class TickerSymbolRepositoryImpl implements TickerSymbolRepository {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TickerSymbol persist(TickerSymbol myTickerSymbolEntity) {
        entityManager.persist(myTickerSymbolEntity);
        entityManager.flush();
        log.info("persisted: "+myTickerSymbolEntity.toString());
        return myTickerSymbolEntity;
    }

    @Override
    public TickerSymbol merge(TickerSymbol tickerSymbol) {
        tickerSymbol= entityManager.merge(tickerSymbol);
        entityManager.flush();
        log.info("merged: "+tickerSymbol.toString());
        return tickerSymbol;
    }

    @Override
    public TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url) {
        try {
            String name = "TickerSymbol.findByTickerSymbolAndUrl";
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("tickerSymbol", tickerSymbol);
            query.setParameter("url", url);
            TickerSymbol result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + tickerSymbol);
            log.info("not found: " + url);
            throw new FindTickerSymbolByTickerSymbolAndUrlException(e, tickerSymbol, url);
        }
    }
}
