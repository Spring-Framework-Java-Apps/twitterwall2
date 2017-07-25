package org.woehlke.twitterwall.scheduled.service.facade;

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
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 11.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class FetchUsersFromDefinedUserListTest  extends AbstractFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(FetchUsersFromDefinedUserListTest.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private StartTask startTask;

    @Ignore
    @Commit
    @Test
    public void fetchUsersFromDefinedUserListTest(){
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.startTask.fetchUsersFromDefinedUserList();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
