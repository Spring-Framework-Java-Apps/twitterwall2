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
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskStartFireAndForgetTestImpl extends AbstractMqEndpointTest implements TaskStartFireAndForgetTest {

    private static final Logger log = LoggerFactory.getLogger(TaskStartFireAndForgetTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private TaskStartFireAndForget mqTaskStartFireAndForget;

    @Test
    public void test001checkDependentComponents(){
        String msg = "updateTweetsTest: ";
        log.debug(msg+"START TEST");
        assertThat(countedEntitiesService).isNotNull();
        assertThat(mqTaskStartFireAndForget).isNotNull();
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test010updateTweetsTest() throws Exception {
        String msg = "updateTweetsTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStartFireAndForget.updateTweets();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.updateUsers();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.updateUsersFromMentions();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchTweetsFromSearch();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_TWEETS_FROM_SEARCH,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    public void test014fetchUsersFromListTest() throws Exception {
        String msg = "fetchTweetsFromSearchTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStartFireAndForget.fetchUsersFromList();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.fetchFollower();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_FOLLOWER,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test016fetchFriendsTest() throws Exception {
        String msg = "fetchFriendsTest: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStartFireAndForget.fetchFriends();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.removeOldDataFromStorage();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getHomeTimeline();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getUserTimeline();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_USER_TIMELINE,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }

    @Test
    @Override
    public void test020getMentions() throws Exception {
        String msg = "getUserTimeline: ";
        log.debug(msg+"START TEST");
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqTaskStartFireAndForget.getMentions();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getFavorites();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getRetweetsOfMe();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
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
        Task task = this.mqTaskStartFireAndForget.getLists();
        log.debug(msg+"created Task = "+task.getUniqueId());
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        Assert.assertEquals(TaskSendType.FIRE_AND_FORGET,task.getTaskSendType());
        Assert.assertEquals(TaskType.FETCH_LISTS,task.getTaskType());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntitiesReduced(beforeTest,afterTest);
        Assert.assertTrue(ok);
        log.debug(msg+"FINISHED TEST");
    }
}
