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
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

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
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateTweets();
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Test
    public void updateUsersTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateUsers();
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Test
    public void updateUsersFromMentionsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateUsersFromMentions();
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Test
    public void fetchTweetsFromSearchTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchTweetsFromSearch();
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    @Test
    public void fetchUsersFromListTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchUsersFromList();
        Assert.assertNotNull(task);
        Assert.assertNotNull(task.getUniqueId());
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
