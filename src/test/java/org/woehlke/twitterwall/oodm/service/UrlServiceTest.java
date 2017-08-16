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
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.conf.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.entities.Url;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UrlServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest,DomainServiceWithUrlTest {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceTest.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(urlService);
        Assert.assertNotNull(testdataProperties);
    }

    @Test
    public void fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Url> myPage = urlService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Url myUrl = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myUrl);
            Assert.assertNotNull(msg,myUrl.getUniqueId());
            log.debug(msg+" found: "+myUrl.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    public void findByUrl() throws Exception {
        String msg = "findByUrl: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Url> myPage = urlService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Url myMedia = myPage.getContent().iterator().next();
            String expectedUrl = myMedia.getUrl();
            Url myFoundMedia = urlService.findByUrl(expectedUrl);
            String foundUrl = myFoundMedia.getUrl();
            Assert.assertEquals(msg, expectedUrl, foundUrl);
            log.debug(msg+" found: "+foundUrl);
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Test
    @Override
    public void findById() throws Exception {

    }

    @Test
    @Override
    public void getAll() throws Exception {

    }

    @Test
    @Override
    public void count() throws Exception {

    }

    @Test
    @Override
    public void findByUniqueId() throws Exception {

    }

    @Test
    @Override
    public void store() throws Exception {

    }

    @Test
    @Override
    public void create() throws Exception {

    }

    @Test
    @Override
    public void update() throws Exception {

    }
}
