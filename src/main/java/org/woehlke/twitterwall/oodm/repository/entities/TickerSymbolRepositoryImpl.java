package org.woehlke.twitterwall.oodm.repository.entities;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TickerSymbol persist(TickerSymbol myTickerSymbolEntity) {
        entityManager.persist(myTickerSymbolEntity);
        return myTickerSymbolEntity;
    }

    @Override
    public TickerSymbol merge(TickerSymbol tickerSymbol) {
        return entityManager.merge(tickerSymbol);
    }

    @Override
    public TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url) {
        try {
            String SQL = "select t from TickerSymbol as t where t.tickerSymbol=:tickerSymbol and t.url=:url";
            TypedQuery<TickerSymbol> query = entityManager.createQuery(SQL, TickerSymbol.class);
            query.setParameter("tickerSymbol", tickerSymbol);
            query.setParameter("url", url);
            TickerSymbol result = query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            throw new FindTickerSymbolByTickerSymbolAndUrlException(e, tickerSymbol, url);
        }
    }
}
