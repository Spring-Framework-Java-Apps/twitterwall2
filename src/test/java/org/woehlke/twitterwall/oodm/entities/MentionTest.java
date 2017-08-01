package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;

public class MentionTest implements DomainObjectMinimalTest  {

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
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
        String name = "Google";
        String name3 = "Java";

        Mention mention1 = new Mention(createdBy,updatedBy,idTwitter1,screenName1,name);
        String expectedUniqueId1 = idTwitter1.toString() +"_"+ screenName1.toString();
        Assert.assertEquals(msg,expectedUniqueId1,mention1.getUniqueId());

        Mention mention2 = new Mention(createdBy,updatedBy,screenName2);
        String expectedUniqueId2 = idTwitter2.toString() +"_"+ screenName2.toString();
        Assert.assertEquals(msg,expectedUniqueId2,mention2.getUniqueId());

        Mention mention3 = new Mention(createdBy,updatedBy,idTwitter3,screenName3,name3);
        String expectedUniqueId3 = idTwitter3.toString() +"_"+ screenName3.toString();
        Assert.assertEquals(msg,expectedUniqueId3,mention3.getUniqueId());
    }

    //TODO: #197 https://github.com/phasenraum2010/twitterwall2/issues/197
    @Test
    @Override
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
        String name = "Google";
        String name3 = "Java";
        String invalidScreenName = "3765726öäöäß%$dsffsdf";

        Assert.assertTrue(msg,Mention.isValidScreenName(screenName1));
        Assert.assertTrue(msg,Mention.isValidScreenName(screenName2));
        Assert.assertTrue(msg,Mention.isValidScreenName(screenName3));
        Assert.assertFalse(msg,Mention.isValidScreenName(screenName4));
        Assert.assertFalse(msg,Mention.isValidScreenName(invalidScreenName));

        Mention mention1 = new Mention(createdBy,updatedBy,idTwitter1,screenName1,name);
        Mention mention2 = new Mention(createdBy,updatedBy,screenName2);
        Mention mention3 = new Mention(createdBy,updatedBy,idTwitter3,screenName3,name3);

        Assert.assertTrue(msg,mention1.isValid());
        Assert.assertTrue(msg,mention2.isValid());
        Assert.assertTrue(msg,mention3.isValid());

        mention1.setScreenName(null);
        mention2.setScreenName(invalidScreenName);
        mention3.setIdTwitter(null);

        Mention mention4 = new Mention(createdBy,updatedBy,idTwitter2,screenName3,name3);
        Mention mention5 = new Mention(createdBy,updatedBy,idTwitter2,screenName4,name3);

        mention4.setIdTwitter(idTwitter4);
        mention5.setIdTwitter(idTwitter4);

        Assert.assertFalse(msg,mention1.isValid());
        Assert.assertFalse(msg,mention2.isValid());

        Assert.assertFalse(msg,mention3.isValid());
        Assert.assertFalse(msg,mention4.isValid());
        Assert.assertFalse(msg,mention5.isValid());
    }
}
