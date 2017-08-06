package org.woehlke.twitterwall.scheduled.mq.endpoint;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class StartTaskTestImpl extends AbstractMqEndpointTest implements StartTaskTest {

    private static final Logger log = LoggerFactory.getLogger(StartTaskTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private StartTask mqStartTask;

    @Autowired
    private FrontendProperties frontendProperties;

    @Test
    public void updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.updateTweets();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void updateUsersTest() throws Exception {
        String msg = "updateUsersTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.updateUsers();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void updateUsersFromMentionsTest() throws Exception {
        String msg = "updateUsersFromMentionsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.updateUsersFromMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void fetchTweetsFromSearchTest() throws Exception {
        String msg = "fetchTweetsFromSearchTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.fetchTweetsFromSearch();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void fetchUsersFromListTest() throws Exception {
        String msg = "fetchUsersFromListTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.fetchUsersFromList();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void createImprintUserTest() throws Exception {
        String msg = "createImprintUserTest: ";
        log.info(msg+"START TEST");
        User user = this.mqStartTask.createImprintUser();
        log.info(msg+"created User = "+user.getUniqueId());
        String screenName = frontendProperties.getImprintScreenName();
        Assert.assertEquals(user.getScreenName(),screenName);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void createTestDataUsersTest() throws Exception {
        String msg = "createTestDataUsersTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.createTestDataForUser();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void createTestDataTweetsTest() throws Exception {
        String msg = "createTestDataTweetsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.createTestDataForTweets();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertEquals(SendType.SEND_AND_WAIT_FOR_RESULT,task.getSendType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }
}
