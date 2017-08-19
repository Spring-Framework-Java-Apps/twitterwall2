package org.woehlke.twitterwall.scheduled.mq.endpoint;


import org.junit.Assert;
import org.junit.Ignore;
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
import org.woehlke.twitterwall.oodm.entities.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.entities.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.mq.endpoint.tasks.StartTask;

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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_TWEETS,task.getTaskType());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_USERS,task.getTaskType());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_MENTIONS_FOR_USERS,task.getTaskType());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_TWEETS_FROM_SEARCH,task.getTaskType());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_USERS_FROM_LIST,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }


    @Test
    @Override
    public void fetchFollowerTest() throws Exception {
        String msg = "fetchFollowerTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.fetchFollower();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FOLLOWER,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void fetchFriendsTest() throws Exception {
        String msg = "fetchFollowerTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.fetchFriends();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FRIENDS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }


    //TODO: #229 https://github.com/phasenraum2010/twitterwall2/issues/229
    @Ignore
    @Test
    @Override
    public void removeOldDataFromStorageTest() throws Exception {
        String msg = "removeOldDataFromStorageTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.removeOldDataFromStorage();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.REMOVE_OLD_DATA_FROM_STORAGE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getHomeTimeline() throws Exception {
        String msg = "getHomeTimeline: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getHomeTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_HOME_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getUserTimeline() throws Exception {
        String msg = "getUserTimeline: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getUserTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_USER_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getMentions() throws Exception {
        String msg = "getMentions: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_MENTIONS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getFavorites() throws Exception {
        String msg = "getFavorites: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getFavorites();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FAVORITES,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getRetweetsOfMe() throws Exception {
        String msg = "getRetweetsOfMe: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getRetweetsOfMe();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_RETWEETS_OF_ME,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getLists() throws Exception {
        String msg = "getLists: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqStartTask.getLists();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_LISTS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void createImprintUserTest() throws Exception {
        String msg = "createImprintUserTest: ";
        log.info(msg+"START TEST");
        User user = this.mqStartTask.createImprintUser();
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUniqueId());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.CREATE_TESTDATA_USERS,task.getTaskType());
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
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.CREATE_TESTDATA_TWEETS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }
}
