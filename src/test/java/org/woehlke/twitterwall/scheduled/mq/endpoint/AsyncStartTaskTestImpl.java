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
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.scheduled.mq.msg.SendType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class AsyncStartTaskTestImpl extends AbstractMqEndpointTest implements AsyncStartTaskTest {

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private AsyncStartTask mqAsyncStartTask;

    @Test
    public void updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateTweets();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.updateUsers();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.updateUsersFromMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.fetchTweetsFromSearch();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
        Assert.assertEquals(TaskType.FETCH_TWEETS_FROM_SEARCH,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    public void fetchUsersFromListTest() throws Exception {
        String msg = "fetchTweetsFromSearchTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchUsersFromList();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.fetchFollower();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
        Assert.assertEquals(TaskType.FETCH_FOLLOWER,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void fetchFriendsTest() throws Exception {
        String msg = "fetchFriendsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchFriends();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.removeOldDataFromStorage();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.getHomeTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.getUserTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
        Assert.assertEquals(TaskType.FETCH_USER_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void getMentions() throws Exception {
        String msg = "getUserTimeline: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.getMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.getFavorites();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.getRetweetsOfMe();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
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
        Task task = this.mqAsyncStartTask.getLists();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(SendType.FIRE_AND_FORGET,task.getSendType());
        Assert.assertEquals(TaskType.FETCH_LISTS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }
}
