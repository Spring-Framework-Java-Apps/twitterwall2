package org.woehlke.twitterwall.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.oodm.service.TweetApiServiceTest;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

import java.util.List;

/**
 * Created by tw on 21.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class TwitterUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TwitterUrlServiceTest.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private TweetApiServiceTest tweetApiServiceTest;

    @Autowired
    private TwitterUrlService twitterUrlService;

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        log.info("fetchTweetsFromTwitterSearchTest: START tweetApiServiceTest.waitForImport()");
        tweetApiServiceTest.waitForImport();
        log.info("fetchTweetsFromTwitterSearchTest: DONE  tweetApiServiceTest.waitForImport()");
        Assert.assertTrue(true);
        log.info("------------------------------------");
    }

    @Commit
    @Test
    public void fetchUrlTest(){
        String msg = "fetchUrlTest ";
        log.info("------------------------------------");
        log.info(msg);
        List<Url> testData = urlService.getTestData();
        for(Url exprected:testData){
            try {
                log.info(msg+"expected: " + exprected.toString());
                Url foundUrl = twitterUrlService.fetchTransientUrl(exprected.getUrl());
                log.info(msg+"found:    " + foundUrl.toString());
                Assert.assertEquals(exprected.getUrl(), foundUrl.getUrl());
                Assert.assertEquals(exprected.getDisplay(),foundUrl.getDisplay());
                Assert.assertEquals(exprected.getExpanded(), foundUrl.getExpanded());
            } catch (FetchUrlException e){
                log.error(msg+e.getMessage());
            }
        }
        log.info("------------------------------------");
    }
    
}
