package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TweetTest implements DomainObjectMinimalTest  {

    private static final Logger log = LoggerFactory.getLogger(TweetTest.class);

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";
        Task createdBy = null;
        Task updatedBy = null;
        Long idTwitter = 57646546476L;
        String idStr = idTwitter.toString();
        String text  = "";
        Date createdAt = new Date();
        Tweet tweet = new Tweet(createdBy,updatedBy,idTwitter,idStr,text,createdAt);
        Assert.assertEquals(msg,idTwitter.toString(),tweet.getUniqueId());
    }

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";
        Task createdBy = null;
        Task updatedBy = null;
        Long idTwitter = 57646546476L;
        String idStr = idTwitter.toString();
        String text  = "";
        Date createdAt = new Date();
        Tweet tweet = new Tweet(createdBy,updatedBy,idTwitter,idStr,text,createdAt);
        Assert.assertEquals(msg,idTwitter.toString(),tweet.getUniqueId());
        tweet.setIdTwitter(null);
        Assert.assertFalse(msg,tweet.isValid());
    }
}
