package org.woehlke.twitterwall.oodm.model;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MentionTest implements DomainObjectMinimalTest  {

    private static final Logger log = LoggerFactory.getLogger(MentionTest.class);

    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        final long ID_TWITTER_UNDEFINED = -1L;

        Task createdBy=null;
        Task updatedBy=null;
        Long idTwitter1 = 20536157L;
        Long idTwitter2 = ID_TWITTER_UNDEFINED;
        Long idTwitter3 = -123L;
        String screenName1 = "Google";
        String screenName2 = "Twitter";
        String screenName3 = "java";
        String screenNameUnique1 = screenName1.toLowerCase();
        String screenNameUnique2 = screenName2.toLowerCase();
        String screenNameUnique3 = screenName3.toLowerCase();
        String name1 = "Google";
        String name2 = "Twitter";
        String name3 = "Java";

        Mention mention1 = new Mention(createdBy,updatedBy,idTwitter1,screenName1,name1);
        String expectedUniqueId1 = idTwitter1.toString() +"_"+ screenNameUnique1;
        Assert.assertEquals(msg,expectedUniqueId1,mention1.getUniqueId());

        Mention mention2 = new Mention(createdBy,updatedBy,screenName2);
        String expectedUniqueId2 = idTwitter2.toString() +"_"+ screenNameUnique2;
        Assert.assertEquals(msg,expectedUniqueId2,mention2.getUniqueId());

        Mention mention3 = new Mention(createdBy,updatedBy,idTwitter3,screenName3,name3);
        String expectedUniqueId3 = idTwitter3.toString() +"_"+ screenNameUnique3.toString();
        Assert.assertEquals(msg,expectedUniqueId3,mention3.getUniqueId());
    }

    @Test
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";

        final long ID_TWITTER_UNDEFINED = -1L;

        Task createdBy=null;
        Task updatedBy=null;
        Long idTwitter1 = 20536157L;
        Long idTwitter2 = ID_TWITTER_UNDEFINED;
        Long idTwitter3 = -123L;
        Long idTwitter4 = null;
        String screenName1 = "Google";
        String screenName2 = "Twitter";
        String screenName3 = "java";
        String screenName4 = null;
        String screenNameUnique1 = screenName1.toLowerCase();
        String screenNameUnique2 = screenName2.toLowerCase();
        String screenNameUnique3 = screenName2.toLowerCase();
        String screenNameUnique4 = null;
        String name = "Google";
        String name3 = "Java";
        String invalidScreenName = "3765726öäöäß%$dsffsdf";

        Assert.assertTrue(msg,Mention.isValidScreenName(screenName1));
        Assert.assertTrue(msg,Mention.isValidScreenName(screenName2));
        Assert.assertTrue(msg,Mention.isValidScreenName(screenName3));
        Assert.assertFalse(msg,Mention.isValidScreenName(screenName4));

        Assert.assertTrue(msg,Mention.isValidScreenNameUnique(screenNameUnique1));
        Assert.assertTrue(msg,Mention.isValidScreenNameUnique(screenNameUnique2));
        Assert.assertTrue(msg,Mention.isValidScreenNameUnique(screenNameUnique3));
        Assert.assertFalse(msg,Mention.isValidScreenNameUnique(screenNameUnique4));

        Assert.assertFalse(msg,Mention.isValidScreenName(invalidScreenName));

        Mention mention1 = new Mention(createdBy,updatedBy,idTwitter1,screenName1,name);
        Mention mention2 = new Mention(createdBy,updatedBy,screenName2);
        Mention mention3 = new Mention(createdBy,updatedBy,idTwitter3,screenName3,name3);

        log.info(msg+" mention1 "+mention1.toString()+" "+mention1.getUniqueId()+" "+mention1.isValid());
        Assert.assertTrue(msg,mention1.isValid());

        log.info(msg+" mention2 "+mention2.toString()+" "+mention2.getUniqueId()+" "+mention2.isValid());
        Assert.assertTrue(msg,mention2.isValid());

        log.info(msg+" mention3 "+mention3.toString()+" "+mention3.getUniqueId()+" "+mention3.isValid());
        Assert.assertTrue(msg,mention3.isValid());

        mention1.setScreenName(null);
        mention2.setScreenName(invalidScreenName);
        mention3.setIdTwitter(null);

        Mention mention4 = new Mention(createdBy,updatedBy,idTwitter2,screenName3,name3);
        Mention mention5 = new Mention(createdBy,updatedBy,idTwitter2,screenName4,name3);

        mention4.setIdTwitter(idTwitter4);
        mention5.setIdTwitter(idTwitter4);

        log.info(msg+" mention1 "+mention1.toString()+" "+mention1.getUniqueId()+" "+mention1.isValid());
        Assert.assertFalse(msg,mention1.isValid());

        log.info(msg+" mention2 "+mention2.toString()+" "+mention2.getUniqueId()+" "+mention2.isValid());
        Assert.assertFalse(msg,mention2.isValid());

        log.info(msg+" mention3 "+mention3.toString()+" "+mention3.getUniqueId()+" "+mention3.isValid());
        Assert.assertFalse(msg,mention3.isValid());

        log.info(msg+" mention4 "+mention4.toString()+" "+mention4.getUniqueId()+" "+mention4.isValid());
        Assert.assertFalse(msg,mention4.isValid());

        log.info(msg+" mention5 "+mention5.toString()+" "+mention5.getUniqueId()+" "+mention5.isValid());
        Assert.assertFalse(msg,mention5.isValid());
    }
}
