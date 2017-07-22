package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;
import org.woehlke.twitterwall.oodm.repositories.impl.TickerSymbolRepositoryCustom;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TickerSymbolRepository extends DomainRepository<TickerSymbol>,TickerSymbolRepositoryCustom {

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);

    TickerSymbol findByUrl(String url);

}
