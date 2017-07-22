package org.woehlke.twitterwall.oodm.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class TickerSymbolRepositoryImpl implements TickerSymbolRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public TickerSymbolRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TickerSymbol findByUniqueId(TickerSymbol domainObject) {
        String name="TickerSymbol.findByUniqueId";
        TypedQuery<TickerSymbol> query = entityManager.createNamedQuery(name,TickerSymbol.class);
        query.setParameter("url",domainObject.getUrl());
        query.setParameter("tickerSymbol",domainObject.getTickerSymbol());
        TickerSymbol result = query.getSingleResult();
        return result;
    }
}
