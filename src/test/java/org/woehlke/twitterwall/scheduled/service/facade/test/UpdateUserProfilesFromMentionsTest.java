package org.woehlke.twitterwall.scheduled.service.facade.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.Application;

/**
 * Created by tw on 11.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@DataJpaTest(showSql=false)
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false)
public class UpdateUserProfilesFromMentionsTest {
}
