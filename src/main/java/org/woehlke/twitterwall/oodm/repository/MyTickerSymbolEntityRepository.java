package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyTickerSymbolEntity;

/**
 * Created by tw on 12.06.17.
 */
public interface MyTickerSymbolEntityRepository {

    MyTickerSymbolEntity persist(MyTickerSymbolEntity myTickerSymbolEntity);

    MyTickerSymbolEntity update(MyTickerSymbolEntity myTickerSymbolEntity);
}
