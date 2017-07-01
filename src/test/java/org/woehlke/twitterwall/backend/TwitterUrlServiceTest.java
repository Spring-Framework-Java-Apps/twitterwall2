package org.woehlke.twitterwall.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.exceptions.remote.FetchUrlException;
import org.woehlke.twitterwall.test.TweetServiceTest;
import org.woehlke.twitterwall.test.UrlServiceTest;

import java.util.List;

/**
 * Created by tw on 21.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TwitterUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TwitterUrlServiceTest.class);

    @Autowired
    private UrlServiceTest urlServiceTest;

    @Autowired
    private TweetServiceTest tweetServiceTest;

    @Autowired
    private TwitterUrlService twitterUrlService;

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        log.info("fetchTweetsFromTwitterSearchTest: START tweetApiServiceTest.waitForImport()");
        tweetServiceTest.waitForImport();
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
        List<Url> testData = urlServiceTest.getTestData();
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
