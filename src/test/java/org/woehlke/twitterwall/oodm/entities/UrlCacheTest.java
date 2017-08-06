package org.woehlke.twitterwall.oodm.entities;

import org.junit.Assert;
import org.junit.Test;

public class UrlCacheTest implements DomainObjectMinimalTest  {

    @Test
    @Override
    public void getUniqueIdTest() throws Exception {
        String msg = "getUniqueIdTest: ";

        Task createdBy=null;
        Task updatedBy=null;
        String urlUrl = "https://t.co/qCd5aTUWEr";
        String urlExpanded = "https://thomas-woehlke.blogspot.de/";

        UrlCache url = new UrlCache(createdBy,updatedBy,urlUrl);

        Assert.assertEquals(msg,urlUrl,url.getUniqueId());
    }

    @Test
    @Override
    public void isValidTest() throws Exception {
        String msg = "isValidTest: ";

        Task createdBy=null;
        Task updatedBy=null;
        String urlUrl = "https://t.co/qCd5aTUWEr";
        String urlExpanded = "https://thomas-woehlke.blogspot.de/";
        String urlDisplay = "thomas-woehlke.blogspot.de";

        UrlCache url = new UrlCache(createdBy,updatedBy,urlUrl);

        Assert.assertTrue(msg,url.isValid());
        Assert.assertTrue(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setExpanded(urlExpanded);
        Assert.assertTrue(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setExpanded(urlUrl);
        Assert.assertTrue(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertTrue(msg,url.isUrlAndExpandedTheSame());

        url.setUrl(null);
        Assert.assertFalse(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());

        url.setUrl("bdfjhbvjhbve837783786hbcd");
        Assert.assertFalse(msg,url.isValid());
        Assert.assertFalse(msg,url.isRawUrlsFromDescription());
        Assert.assertFalse(msg,url.isUrlAndExpandedTheSame());
    }
}
