package org.woehlke.twitterwall.oodm.repositories.impl;

import org.woehlke.twitterwall.oodm.entities.TickerSymbol;

public interface TickerSymbolRepositoryCustom {

    TickerSymbol findByUniqueId(TickerSymbol domainObject);
}
