package org.woehlke.twitterwall.scheduled.mq.endpoint;


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
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=true)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class StartTaskTestImpl extends AbstractMqEndpointTest implements StartTaskTest {

    private static final Logger log = LoggerFactory.getLogger(StartTaskTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private StartTask mqStartTask;

    @Autowired
    private FrontendProperties frontendProperties;

    @Autowired
    private SchedulerProperties schedulerProperties;

    @Commit
    @Test
    public void updateTweetsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqStartTask.updateTweets();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Commit
    @Test
    public void updateUsersTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqStartTask.updateUsers();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Commit
    @Test
    public void updateUsersFromMentionsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqStartTask.updateUsersFromMentions();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Commit
    @Test
    public void fetchTweetsFromSearchTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqStartTask.fetchTweetsFromSearch();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Commit
    @Test
    public void fetchUsersFromListTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqStartTask.fetchUsersFromList();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Commit
    @Test
    public void createImprintUserTest() throws Exception {
        User user = this.mqStartTask.createImprintUser();
        String screenName = frontendProperties.getImprintScreenName();
        Assert.assertEquals(user.getScreenName(),screenName);
    }

    @Commit
    @Test
    public void createTestDataUsersTest() throws Exception {
        List<User> userList = this.mqStartTask.createTestDataForUser();
        Assert.assertTrue("mqStartTask.createTestDataForUser() > 0 ",userList.size()>0);
        int expectedSize = schedulerProperties.getFacade().getIdTwitterToFetchForUserControllerTest().size();
        String msg = "mqStartTask.createTestDataForUser() == "+expectedSize;
        Assert.assertTrue(msg,userList.size()==expectedSize);
    }

    @Commit
    @Test
    public void createTestDataTweetsTest() throws Exception {
        List<Tweet> tweetList = this.mqStartTask.createTestDataForTweets();
        Assert.assertTrue("mqStartTask.createTestDataForTweets() > 0 ",tweetList.size()>0);
        int expectedSize = schedulerProperties.getFacade().getIdTwitterToFetchForTweetTest().size();
        String msg = "mqStartTask.createTestDataForTweets() == "+expectedSize;
        Assert.assertTrue(msg,tweetList.size()==expectedSize);
    }
}
