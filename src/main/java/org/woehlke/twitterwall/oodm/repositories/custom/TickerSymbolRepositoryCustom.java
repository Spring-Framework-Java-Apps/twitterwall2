package org.woehlke.twitterwall.oodm.repositories.custom;

import org.woehlke.twitterwall.oodm.model.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.common.DomainObjectEntityRepository;

public interface TickerSymbolRepositoryCustom extends DomainObjectEntityRepository<TickerSymbol> {

    TickerSymbol findByUniqueId(TickerSymbol domainObject);
}
