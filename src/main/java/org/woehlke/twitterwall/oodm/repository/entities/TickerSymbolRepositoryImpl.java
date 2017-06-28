package org.woehlke.twitterwall.oodm.repository.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindTickerSymbolByTickerSymbolAndUrlException;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryImpl;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitterImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Repository                                                                          
public class TickerSymbolRepositoryImpl extends DomainRepositoryImpl<TickerSymbol> implements TickerSymbolRepository {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

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
            throw e;
        }
    }

    @Override
    public TickerSymbol findByUrl(String url) {
        try {
            String name = "TickerSymbols.findByUrl";
            TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name, TickerSymbol.class);
            query.setParameter("url", url);
            TickerSymbol result = query.getSingleResult();
            log.info("found: " + result.toString());
            return result;
        } catch (NoResultException e) {
            log.info("not found: " + url);
            throw e;
        }
    }
}
