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
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest,DomainServiceWithUrlTest {

    private static final Logger log = LoggerFactory.getLogger(UrlServiceTest.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(urlService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
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

    @Commit
    @Test
    public void test025findByUrl() throws Exception {
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

    @Commit
    @Test
    public void findRawUrlsFromDescription() throws Exception {
        String msg = "findRawUrlsFromDescription: ";
        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task createdBy= taskService.create(msg, TaskType.NULL, TaskSendType.NO_MQ,countedEntities);
        Task updatedBy=null;
        String display=Url.UNDEFINED;
        String expanded=Url.UNDEFINED;
        String url1="http://woehlke.org/";
        String url2="http://thomas-woehlke.de";
        String url3="http://java.sun.com";
        String url4="http://tomcat.apache.org";

        Url urlTest1 = new Url(createdBy,updatedBy,display,expanded,url1);
        Url urlTest2 = new Url(createdBy,updatedBy,display,expanded,url2);
        Url urlTest3 = new Url(createdBy,updatedBy,url3,url3,url3);
        Url urlTest4 = new Url(createdBy,updatedBy,url4,url4,url4);

        urlService.store(urlTest1,createdBy);
        urlService.store(urlTest2,createdBy);
        urlService.store(urlTest3,createdBy);
        urlService.store(urlTest4,createdBy);

        List<Url> urlList = urlService.findRawUrlsFromDescription();
        log.debug(msg+"+++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+" size: "+urlList.size());
        log.debug(msg+"+++++++++++++++++++++++++++++++++++++++++");
        Assert.assertTrue(urlList.size()>0);
        for(Url url:urlList){
            Assert.assertTrue(url.isValid());
            Assert.assertTrue(url.isRawUrlsFromDescription());
            Assert.assertTrue(url.getExpanded().compareTo(Url.UNDEFINED)==0);
            Assert.assertTrue(url.getDisplay().compareTo(Url.UNDEFINED)==0);
            log.debug(msg+"-----------------------------------------");
            log.debug(msg+url.getUniqueId());
            log.debug(msg+"-----------------------------------------");
            log.trace(msg+url.toString());
        }
    }

    @Commit
    @Test
    public void findUrlAndExpandedTheSame() throws Exception {
        String msg = "findUrlAndExpandedTheSame: ";

        CountedEntities countedEntities = countedEntitiesService.countAll();
        Task createdBy= taskService.create(msg, TaskType.NULL, TaskSendType.NO_MQ,countedEntities);
        Task updatedBy=null;
        String display=Url.UNDEFINED;
        String expanded=Url.UNDEFINED;
        String url1="http://woehlke.org/";
        String url2="http://thomas-woehlke.de";
        String url3="http://java.sun.com";
        String url4="http://tomcat.apache.org";

        Url urlTest1 = new Url(createdBy,updatedBy,display,expanded,url1);
        Url urlTest2 = new Url(createdBy,updatedBy,display,expanded,url2);
        Url urlTest3 = new Url(createdBy,updatedBy,url3,url3,url3);
        Url urlTest4 = new Url(createdBy,updatedBy,url4,url4,url4);

        urlService.store(urlTest1,createdBy);
        urlService.store(urlTest2,createdBy);
        urlService.store(urlTest3,createdBy);
        urlService.store(urlTest4,createdBy);

        List<Url> urlList = urlService.findUrlAndExpandedTheSame();
        log.debug(msg+"+++++++++++++++++++++++++++++++++++++++++");
        log.debug(msg+" size: "+urlList.size());
        log.debug(msg+"+++++++++++++++++++++++++++++++++++++++++");
        Assert.assertTrue(urlList.size()>0);
        for(Url url:urlList){
            Assert.assertTrue(url.isValid());
            Assert.assertTrue(url.isUrlAndExpandedTheSame());
            Assert.assertTrue(url.getUrl().compareTo(url.getExpanded())==0);
            log.debug(msg+"-----------------------------------------");
            log.debug(msg+url.getUniqueId());
            log.debug(msg+"-----------------------------------------");
            log.trace(msg+url.toString());
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
