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
import org.woehlke.twitterwall.oodm.entities.application.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfiles;
import org.woehlke.twitterwall.scheduled.service.facade.common.AbstractFacadeTest;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 11.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UpdateUserProfilesTest extends AbstractFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesTest.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private UpdateUserProfiles updateUserProfiles;

    @Commit
    @Test
    public void updateUserProfiles(){
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.updateUserProfiles.updateUserProfiles();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
