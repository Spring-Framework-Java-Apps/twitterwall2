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
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=true)
public class AsyncStartTaskTestImpl extends AbstractMqEndpointTest implements AsyncStartTaskTest {

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private AsyncStartTask mqAsyncStartTask;

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    //@Ignore
    //@Commit
    @Test
    public void updateTweetsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateTweets();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    //@Ignore
    //@Commit
    @Test
    public void updateUsersTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateUsers();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    //@Ignore
    //@Commit
    @Test
    public void updateUsersFromMentionsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.updateUsersFromMentions();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    //@Ignore
    //@Commit
    @Test
    public void fetchTweetsFromSearchTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchTweetsFromSearch();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    //@Ignore
    //@Commit
    @Test
    public void fetchUsersFromListTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        Task task = this.mqAsyncStartTask.fetchUsersFromList();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
