package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.repositories.common.DomainRepository;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TickerSymbolRepository extends DomainRepository<TickerSymbol> {

    @Query(name="TickerSymbol.findByUniqueId")
    TickerSymbol findByUniqueId(@Param("domainObject") TickerSymbol domainObject);

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);

    TickerSymbol findByUrl(String url);

}
