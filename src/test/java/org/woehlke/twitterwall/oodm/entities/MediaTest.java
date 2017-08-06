package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;

public class MediaTest implements DomainObjectMinimalTest  {

    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        Task createdBy=null;
        Task updatedBy=null;
        String url="https://t.co/fnCcy3RmuV";
        String mediaType="photo";
        String mediaHttps = "https://pbs.twimg.com/media/DClxcLmXsAAW1t6.jpg";
        String mediaHttp = "http://pbs.twimg.com/media/DClxcLmXsAAW1t6.jpg";
        Long idTwitter1 = -1L;
        Long idTwitter2 = 876356331464273920L;
        String expanded = "https://twitter.com/port80guru/status/876356335784394752/photo/1";
        String display = "pic.twitter.com/fnCcy3RmuV";

        Media medium1 = new Media(createdBy,updatedBy,url);
        String expectedUniqueId1 = idTwitter1.toString();
        Assert.assertEquals(msg,expectedUniqueId1,medium1.getUniqueId());

        Media medium2 = new Media(createdBy, updatedBy, idTwitter2, mediaHttp, mediaHttps, url, display, expanded, mediaType);
        String expectedUniqueId2 = idTwitter2.toString();
        Assert.assertEquals(msg,expectedUniqueId2,medium2.getUniqueId());
    }

    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";

        Task createdBy=null;
        Task updatedBy=null;
        String urlValid1 ="https://t.co/fnCcy3RmuV";
        String urlNotValid1 ="vgfgcgfcgcf";
        String mediaType="photo";
        String mediaHttps = "https://pbs.twimg.com/media/DClxcLmXsAAW1t6.jpg";
        String mediaHttp = "http://pbs.twimg.com/media/DClxcLmXsAAW1t6.jpg";
        Long idTwitter = 876356331464273920L;
        String expanded = "https://twitter.com/port80guru/status/876356335784394752/photo/1";
        String display = "pic.twitter.com/fnCcy3RmuV";

        Media medium1 = new Media(createdBy,updatedBy, urlValid1);
        Media medium2 = new Media(createdBy, updatedBy, idTwitter, mediaHttp, mediaHttps, urlValid1, display, expanded, mediaType);

        Assert.assertTrue(msg,medium1.isValid());
        Assert.assertTrue(msg,medium2.isValid());

        medium1.setUrl(urlNotValid1);
        medium2.setUrl(urlNotValid1);
        Assert.assertFalse(msg,medium1.isValid());
        Assert.assertFalse(msg,medium2.isValid());
    }
}
