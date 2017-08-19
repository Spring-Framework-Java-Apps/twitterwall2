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
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TaskStartFireAndForgetTestImpl extends AbstractMqEndpointTest implements TaskStartFireAndForgetTest {

    private static final Logger log = LoggerFactory.getLogger(TaskStartFireAndForgetTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TaskStartFireAndForget mqTaskStartFireAndForget;

    @Test
    public void updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.info(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStartFireAndForget.updateTweets();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.updateUsers();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.updateUsersFromMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchTweetsFromSearch();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchUsersFromList();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchFollower();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchFriends();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.removeOldDataFromStorage();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getHomeTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getUserTimeline();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getMentions();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getFavorites();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getRetweetsOfMe();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getLists();
        log.info(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_LISTS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.info(msg+"FINISHED TEST");
    }
}