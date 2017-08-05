package org.woehlke.twitterwall.oodm.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.UrlCache;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UrlCacheServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UrlCacheServiceTest.class);

    @Autowired
    private UrlCacheService urlCacheService;

    //TODO: #198 https://github.com/phasenraum2010/twitterwall2/issues/198
    @Autowired
    private TestdataProperties testdataProperties;

    //@Commit
    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(urlCacheService);
        Assert.assertNotNull(testdataProperties);
    }

    //@Commit
    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<UrlCache> myPage = urlCacheService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            UrlCache myUrlCache = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myUrlCache);
            Assert.assertNotNull(msg,myUrlCache.getUniqueId());
            log.debug(msg+" found: "+myUrlCache.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    //@Commit
    @Test
    public void findByUrl() throws Exception {
        String msg = "findByUrl: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<UrlCache> myPage = urlCacheService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            UrlCache myMedia = myPage.getContent().iterator().next();
            String expectedUrl = myMedia.getUrl();
            UrlCache myFoundMedia = urlCacheService.findByUrl(expectedUrl);
            String foundUrl = myFoundMedia.getUrl();
            Assert.assertEquals(msg, expectedUrl, foundUrl);
            log.debug(msg+" found: "+foundUrl);
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

}
