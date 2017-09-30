package org.woehlke.twitterwall.oodm.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.configuration.properties.TestdataProperties;
import org.woehlke.twitterwall.oodm.model.Media;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MediaServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest,DomainServiceWithIdTwitterTest,DomainServiceWithUrlTest {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceTest.class);

    @Autowired
    private MediaService mediaService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(mediaService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Media> myPage = mediaService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Media myMedia = myPage.getContent().iterator().next();
            Assert.assertNotNull(msg,myMedia);
            Assert.assertNotNull(msg,myMedia.getUniqueId());
            log.debug(msg+" found: "+myMedia.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void test020findByIdTwitter() throws Exception {
        String msg = "findByIdTwitter: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Media> myPage = mediaService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Media myMedia = myPage.getContent().iterator().next();
            long expectedIdTwitter = myMedia.getIdTwitter();
            Media myFoundMedia = mediaService.findByIdTwitter(expectedIdTwitter);
            long foundIdTwitter = myFoundMedia.getIdTwitter();
            Assert.assertEquals(msg, expectedIdTwitter,foundIdTwitter);
            log.debug(msg+" found: "+myMedia.getUniqueId());
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    public void test025findByUrl() throws Exception {
        String msg = "findByUrl: ";
        int page=1;
        int size=1;
        Pageable pageRequest = new PageRequest(page,size);
        Page<Media> myPage = mediaService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            Media myMedia = myPage.getContent().iterator().next();
            String expectedUrl = myMedia.getUrl();
            Media myFoundMedia = mediaService.findByUrl(expectedUrl);
            String foundUrl = myFoundMedia.getUrl();
            Assert.assertEquals(msg, expectedUrl, foundUrl);
            log.debug(msg+" found: "+foundUrl);
        } else {
            log.debug(msg+" found: myPage.getTotalElements() == 0");
        }
    }

    @Commit
    @Test
    @Override
    public void test050findById() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test051getAll() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test052count() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test053findByUniqueId() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test100store() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test101create() throws Exception {

    }

    @Commit
    @Test
    @Override
    public void test102update() throws Exception {

    }
}
