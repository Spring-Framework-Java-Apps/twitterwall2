package org.woehlke.twitterwall.oodm.repository.entities;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public TickerSymbol update(TickerSymbol myTickerSymbolEntity) {
        return entityManager.merge(myTickerSymbolEntity);
    }
}
