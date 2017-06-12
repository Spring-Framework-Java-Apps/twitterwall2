package org.woehlke.twitterwall.oodm.repository;

import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.MyTickerSymbolEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by tw on 12.06.17.
 */
@Repository
public class MyTickerSymbolEntityRepositoryImpl implements MyTickerSymbolEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MyTickerSymbolEntity persist(MyTickerSymbolEntity myTickerSymbolEntity) {
        entityManager.persist(myTickerSymbolEntity);
        return myTickerSymbolEntity;
    }

    @Override
    public MyTickerSymbolEntity update(MyTickerSymbolEntity myTickerSymbolEntity) {
        return entityManager.merge(myTickerSymbolEntity);
    }
}
