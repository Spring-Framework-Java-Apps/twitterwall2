package org.woehlke.twitterwall.backend.mq.tasks;


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
import org.woehlke.twitterwall.backend.mq.endpoint.AbstractMqEndpointTest;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TaskStartTestImpl extends AbstractMqEndpointTest implements TaskStartTest {

    private static final Logger log = LoggerFactory.getLogger(TaskStartTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TaskStart mqTaskStart;

    @Autowired
    private FrontendProperties frontendProperties;

    @Test
    public void updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStart.updateTweets();
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
        Task task = this.mqTaskStart.updateUsers();
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
        Task task = this.mqTaskStart.updateUsersFromMentions();
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
        Task task = this.mqTaskStart.fetchTweetsFromSearch();
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
        Task task = this.mqTaskStart.fetchUsersFromList();
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
        Task task = this.mqTaskStart.fetchFollower();
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
        Task task = this.mqTaskStart.fetchFriends();
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
        Task task = this.mqTaskStart.removeOldDataFromStorage();
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
        Task task = this.mqTaskStart.getHomeTimeline();
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
        Task task = this.mqTaskStart.getUserTimeline();
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
        Task task = this.mqTaskStart.getMentions();
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
        Task task = this.mqTaskStart.getFavorites();
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
        Task task = this.mqTaskStart.getRetweetsOfMe();
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
        Task task = this.mqTaskStart.getLists();
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
        User user = this.mqTaskStart.createImprintUser();
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
        Task task = this.mqTaskStart.createTestDataForUser();
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
        Task task = this.mqTaskStart.createTestDataForTweets();
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
