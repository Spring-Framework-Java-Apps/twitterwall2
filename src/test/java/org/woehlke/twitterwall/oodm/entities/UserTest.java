package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by tw on 22.06.17.
 */
public class UserTest implements DomainObjectMinimalTest {

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";
        log.info(msg+"------------------------------------------------");
        Assert.assertTrue(msg,true);
    }

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";
        log.info(msg+"------------------------------------------------");
        Assert.assertTrue(msg,true);
    }
}
