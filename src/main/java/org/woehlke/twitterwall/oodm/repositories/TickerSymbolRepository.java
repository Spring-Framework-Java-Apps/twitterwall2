package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;

/**
 * Created by tw on 15.07.17.
 */
@Repository
public interface TickerSymbolRepository extends PagingAndSortingRepository<TickerSymbol,Long> {

    TickerSymbol findByTickerSymbolAndUrl(String tickerSymbol, String url);

    TickerSymbol findByUrl(String url);

}
