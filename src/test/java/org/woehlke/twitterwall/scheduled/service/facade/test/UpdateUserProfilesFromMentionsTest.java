package org.woehlke.twitterwall.scheduled.service.facade.test;

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
import org.woehlke.twitterwall.scheduled.service.facade.UpdateUserProfilesFromMentions;
import org.woehlke.twitterwall.scheduled.service.facade.common.AbstractFacadeTest;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 11.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UpdateUserProfilesFromMentionsTest extends AbstractFacadeTest {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfilesFromMentionsTest.class);

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Autowired
    private UpdateUserProfilesFromMentions updateUserProfilesFromMentions;

    @Ignore
    @Commit
    @Test
    public void updateUserProfilesFromMentions(){
        CountedEntities beforeTest = countedEntitiesService.countAll();
        this.updateUserProfilesFromMentions.updateUserProfilesFromMentions();
        CountedEntities afterTest = countedEntitiesService.countAll();
        boolean ok = assertCountedEntities(beforeTest,afterTest);
        Assert.assertTrue(ok);
    }
}
