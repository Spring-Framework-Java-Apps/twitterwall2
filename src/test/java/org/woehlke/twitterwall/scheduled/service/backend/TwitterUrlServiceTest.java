package org.woehlke.twitterwall.scheduled.service.backend;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.entities.parts.TaskStatus;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.test.UrlServiceTestHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by tw on 21.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
public class TwitterUrlServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TwitterUrlServiceTest.class);

    @Autowired
    private UrlServiceTestHelper urlServiceTest;

    @Autowired
    private TwitterUrlService twitterUrlService;

    @Ignore
    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        log.info("fetchTweetsFromTwitterSearchTest: START tweetApiServiceTest.waitForImport()");
        //tweetServiceTest.waitForImport();
        log.info("fetchTweetsFromTwitterSearchTest: DONE  tweetApiServiceTest.waitForImport()");
        Assert.assertTrue(true);
        log.info("------------------------------------");
    }

    @Ignore
    @Commit
    @Test
    public void fetchUrlTest(){
        String msg = "fetchUrlTest ";
        log.info("------------------------------------");
        log.info(msg);

        String descriptionTask = "Make it so, Scotty";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH;
        long taskId = 222L;

        TaskStatus taskStatus = TaskStatus.READY;
        Date timeStarted = new Date();
        Date timeLastUpdate = timeStarted;
        Date timeFinished = null;

        Task task = new Task(descriptionTask,taskType,taskStatus,timeStarted,timeLastUpdate,timeFinished);

        //Task task = new Task("", TaskType.NULL);
        List<Url> testData = urlServiceTest.getTestData(task);
        for(Url exprected:testData){
                log.info(msg+"expected: " + exprected.toString());
                Url foundUrl = twitterUrlService.fetchTransientUrl(exprected.getUrl(),task);
                Assert.assertNotNull(foundUrl);
                log.info(msg+"found:    " + foundUrl.toString());
                Assert.assertEquals(exprected.getUrl(), foundUrl.getUrl());
                Assert.assertEquals(exprected.getDisplay(),foundUrl.getDisplay());
                Assert.assertEquals(exprected.getExpanded(), foundUrl.getExpanded());
        }
        log.info("------------------------------------");
    }

}
