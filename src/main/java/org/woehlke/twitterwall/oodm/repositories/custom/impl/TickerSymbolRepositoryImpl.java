package org.woehlke.twitterwall.oodm.repositories.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.custom.TickerSymbolRepositoryCustom;

import javax.persistence.EntityManager;

@Repository
public class TickerSymbolRepositoryImpl extends AbstractDomainRepositoryImpl<TickerSymbol> implements TickerSymbolRepositoryCustom {

    @Autowired
    public TickerSymbolRepositoryImpl(EntityManager entityManager) {
        super( entityManager );
    }

}
