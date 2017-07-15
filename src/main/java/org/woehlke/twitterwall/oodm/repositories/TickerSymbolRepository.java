package org.woehlke.twitterwall.oodm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;

/**
 * Created by tw on 15.07.17.
 */
public interface TickerSymbolRepository extends PagingAndSortingRepository<TickerSymbol,Long> {
}
