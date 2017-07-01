package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.test.TweetServiceTest;
import org.woehlke.twitterwall.test.PersistDataFromTwitterTest;
import org.woehlke.twitterwall.test.UserServiceTest;


import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.woehlke.twitterwall.frontend.controller.TestController.ID_TWITTER_TO_FETCH_FOR_PROFILE_CONTROLLER_TEST;
import static org.woehlke.twitterwall.frontend.controller.TestController.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;
import static org.woehlke.twitterwall.test.ScheduledTasksFacadeTest.*;

/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DataJpaTest(showSql=false)
@Transactional(REQUIRES_NEW)
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
public class ProfileControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ProfileControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProfileController controller;

    @Autowired
    private TweetServiceTest tweetServiceTest;

    @Autowired
    private UserServiceTest userServiceTest;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;
    
    @Test
    public void controllerIsPresentTest() throws Exception {
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    private final static String screenName = "port80guru";

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        tweetServiceTest.createTestData();
        log.info("fetchTweetsFromTwitterSearchTest: START  userServiceTest.createUser("+screenName+")");
        userServiceTest.createUser(screenName);
        log.info("fetchTweetsFromTwitterSearchTest: DONE  userServiceTest.createUser("+screenName+")");
        Assert.assertTrue(true);
        log.info("------------------------------------");
    }
    
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/profile/"+screenName))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("page")).andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");

    }
}
