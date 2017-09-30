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
import org.woehlke.twitterwall.oodm.model.TickerSymbol;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TickerSymbolServiceTest implements DomainObjectMinimalServiceTest,DomainServiceWithTaskTest,DomainServiceWithUrlTest {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceTest.class);

    @Autowired
    private TickerSymbolService tickerSymbolService;

    @Autowired
    private TestdataProperties testdataProperties;

    @Test
    public void test000areDependenciesLoaded() throws Exception {
        Assert.assertNotNull(tickerSymbolService);
        Assert.assertNotNull(testdataProperties);
    }

    @Commit
    @Test
    public void test001fetchTestData() throws Exception {
        String msg = "fetchTestData: ";
        int page=1;
        int size=20;
        Pageable pageRequest = new PageRequest(page,size);
        Page<TickerSymbol> myPage = tickerSymbolService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            for(TickerSymbol myTask :myPage.getContent()){
                Assert.assertNotNull(msg,myTask);
                Assert.assertNotNull(msg,myTask.getUniqueId());
                log.debug(msg+" found: "+myTask.getUniqueId());
            }
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
        Page<TickerSymbol> myPage = tickerSymbolService.getAll(pageRequest);
        if(myPage.getTotalElements()>0){
            for(TickerSymbol myMedia :myPage.getContent()){
                String expectedUrl = myMedia.getUrl();
                TickerSymbol myFoundMedia = tickerSymbolService.findByUrl(expectedUrl);
                String foundUrl = myFoundMedia.getUrl();
                log.debug(msg+" found: "+foundUrl);
                Assert.assertEquals(msg, expectedUrl, foundUrl);
            }
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
