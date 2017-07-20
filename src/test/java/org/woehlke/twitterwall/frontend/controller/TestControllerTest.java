package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.PrepareDataTest;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.facade.FetchUsersFromDefinedUserList;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by tw on 01.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestControllerTest extends PrepareDataTest {

    private static final Logger log = LoggerFactory.getLogger(TestControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestController controller;

    @Autowired
    private TwitterApiService twitterApiService;

    @Autowired
    private StoreOneTweet storeOneTweet;

    @Autowired
    private StoreUserProfile storeUserProfile;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FetchUsersFromDefinedUserList fetchUsersFromDefinedUserList;

    @Value("${twitterwall.frontend.menuAppName}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.infoWebpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.frontend.contextTest}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.imprintScreenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.imprintSubtitle}")
    private String imprintSubtitle;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;


    @Autowired
    private ConfigTwitterwall configTwitterwall;

    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSetWithTesting(configTwitterwall,taskService,twitterApiService,storeOneTweet,storeUserProfile,userService,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }

    @Commit
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void setupTestData(){
        String msg = "setupTestData: ";
        super.getTestDataTweets(msg);
        super.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void getTestDataTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/test/getTestData"))
                .andExpect(status().isOk())
                .andExpect(view().name( "/test/getTestData"))
                .andExpect(model().attributeExists("latestTweets"))
                //.andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void getOnListRenewTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/test/user/onlist/renew"))
            .andExpect(status().isOk())
            .andExpect(view().name( "/test/user/onlist/renew"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");
        Assert.assertTrue(true);
    }

}
