package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.junit.Assert;
import org.junit.Ignore;
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
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=true)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class AsyncStartTaskTestImpl extends AbstractMqEndpointTest implements AsyncStartTaskTest {

    private static final Logger log = LoggerFactory.getLogger(AsyncStartTaskTestImpl.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private AsyncStartTask mqAsyncStartTask;

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    @Ignore
    @Commit
    @Test
    public void updateTweetsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqAsyncStartTask.updateTweets();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    @Ignore
    @Commit
    @Test
    public void updateUserProfilesTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqAsyncStartTask.updateUserProfiles();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    @Ignore
    @Commit
    @Test
    public void updateUserProfilesFromMentionsTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqAsyncStartTask.updateUserProfilesFromMentions();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    @Ignore
    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqAsyncStartTask.fetchTweetsFromTwitterSearch();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }

    //TODO: #200 https://github.com/phasenraum2010/twitterwall2/issues/200
    @Ignore
    @Commit
    @Test
    public void fetchUsersFromDefinedUserListTest() throws Exception {
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.mqAsyncStartTask.fetchUsersFromDefinedUserList();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
