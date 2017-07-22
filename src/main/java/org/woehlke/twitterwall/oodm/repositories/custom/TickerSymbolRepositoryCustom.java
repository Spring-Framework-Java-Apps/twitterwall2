package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectMinimalRepository;

public interface TickerSymbolRepositoryCustom extends DomainObjectMinimalRepository<TickerSymbol> {

    TickerSymbol findByUniqueId(TickerSymbol domainObject);
}
