package org.woehlke.twitterwall.backend.mq.tasks;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.backend.mq.endpoint.AbstractMqEndpointTest;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskStartTestImpl extends AbstractMqEndpointTest implements TaskStartTest {

    private static final Logger log = LoggerFactory.getLogger(TaskStartTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TaskStart mqTaskStart;

    @Autowired
    private FrontendProperties frontendProperties;

    @Test
    public void test010updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.updateTweets();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_TWEETS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test011updateUsersTest() throws Exception {
        String msg = "updateUsersTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.updateUsers();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_USERS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test012updateUsersFromMentionsTest() throws Exception {
        String msg = "updateUsersFromMentionsTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.updateUsersFromMentions();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.UPDATE_MENTIONS_FOR_USERS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test013fetchTweetsFromSearchTest() throws Exception {
        String msg = "fetchTweetsFromSearchTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.fetchTweetsFromSearch();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_TWEETS_FROM_SEARCH,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test014fetchUsersFromListTest() throws Exception {
        String msg = "fetchUsersFromListTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.fetchUsersFromList();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_USERS_FROM_LIST,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }


    @Test
    @Override
    public void test015fetchFollowerTest() throws Exception {
        String msg = "fetchFollowerTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.fetchFollower();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FOLLOWER,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test016fetchFriendsTest() throws Exception {
        String msg = "fetchFollowerTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.fetchFriends();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FRIENDS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }


    //TODO: #229 https://github.com/phasenraum2010/twitterwall2/issues/229
    @Ignore
    @Test
    @Override
    public void test017removeOldDataFromStorageTest() throws Exception {
        String msg = "removeOldDataFromStorageTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.removeOldDataFromStorage();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.REMOVE_OLD_DATA_FROM_STORAGE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test018getHomeTimeline() throws Exception {
        String msg = "getHomeTimeline: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getHomeTimeline();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_HOME_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test019getUserTimeline() throws Exception {
        String msg = "getUserTimeline: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getUserTimeline();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_USER_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test020getMentions() throws Exception {
        String msg = "getMentions: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getMentions();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_MENTIONS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test021getFavorites() throws Exception {
        String msg = "getFavorites: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getFavorites();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FAVORITES,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test022getRetweetsOfMe() throws Exception {
        String msg = "getRetweetsOfMe: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getRetweetsOfMe();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_RETWEETS_OF_ME,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test023getLists() throws Exception {
        String msg = "getLists: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.getLists();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_LISTS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test100createImprintUserTest() throws Exception {
        String msg = "createImprintUserTest: ";
        log.debug(msg+"START TEST");
        User user = this.mqTaskStart.createImprintUser();
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUniqueId());
        log.debug(msg+"created User = "+user.getUniqueId());
        String screenName = frontendProperties.getImprintScreenName();
        Assert.assertEquals(user.getScreenName(),screenName);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test110createTestDataUsersTest() throws Exception {
        String msg = "createTestDataUsersTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.createTestDataForUser();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.CREATE_TESTDATA_USERS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test120createTestDataTweetsTest() throws Exception {
        String msg = "createTestDataTweetsTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.createTestDataForTweets();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.SEND_AND_WAIT_FOR_RESULT,task.getTaskSendType());
        Assert.assertEquals(TaskType.CREATE_TESTDATA_TWEETS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }
}
