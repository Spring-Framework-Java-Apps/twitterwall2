package org.woehlke.twitterwall.oodm.model;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TickerSymbolTest implements DomainObjectMinimalTest  {

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolTest.class);

    @Test
    @Override
    public void test001getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        Task createdBy = null;
        Task updatedBy = null;
        String tickerSymbolString1 = ":-)";
        String tickerSymbolString2 = "UNDEFINED";
        String url1 = "https://github.com/phasenraum2010/twitterwall2/issues/197";
        String url2 = "https://community.oracle.com/community/java";

        String myUniqueId1 = "" + url1 + "_" +  tickerSymbolString1;
        String myUniqueId2 = "" + url2 + "_" +  tickerSymbolString2;

        TickerSymbol tickerSymbol1 = new TickerSymbol(createdBy,updatedBy,tickerSymbolString1,url1);

        TickerSymbol tickerSymbol2 = new TickerSymbol(createdBy,updatedBy, url2);

        log.debug(msg+" Expected: "+myUniqueId1+" == Found: "+tickerSymbol1.getUniqueId());
        log.debug(msg+" Expected: "+myUniqueId2+" == Found: "+tickerSymbol2.getUniqueId());

        Assert.assertEquals(msg,myUniqueId1,tickerSymbol1.getUniqueId());
        Assert.assertEquals(msg,myUniqueId2,tickerSymbol2.getUniqueId());
    }

    @Test
    @Override
    public void test002isValidTest() throws Exception {
        String msg = "isValidTest: ";
        Assert.assertTrue(msg,true);
    }
}
