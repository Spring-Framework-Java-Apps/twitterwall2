package org.woehlke.twitterwall.scheduled.service.facade.test;

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
import org.woehlke.twitterwall.ScheduledTasks;
import org.woehlke.twitterwall.oodm.entities.application.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.facade.*;
import org.woehlke.twitterwall.scheduled.service.facade.common.AbstractFacadeTest;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;


/**
 * Created by tw on 11.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class FetchTweetsFromTwitterSearchTest extends AbstractFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private FetchTweetsFromTwitterSearch fetchTweetsFromTwitterSearch;

    @Autowired
    private CountedEntitiesService countedEntitiesService;


    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest(){
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.fetchTweetsFromTwitterSearch.fetchTweetsFromTwitterSearch();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }


}
