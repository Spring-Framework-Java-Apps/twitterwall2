package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.*;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.UserService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController controller;

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulerProperties schedulerProperties;

    @Autowired
    private FrontendProperties frontendProperties;

    @Autowired
    private TwitterProperties twitterProperties;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Commit
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void setupTestData() throws Exception {
        String msg = "setupTestData: ";
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void getAllTest() throws Exception {
        String msg = "getAllTest: ";
        MvcResult result = this.mockMvc.perform(get("/user/all"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/all"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    private User findOneUser(){
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, 1);
        Page<User> tweetPage = userService.getAll(pageRequest);
        if(tweetPage.getContent().size()>0){
            return tweetPage.getContent().iterator().next();
        } else {
            return null;
        }
    }

    @WithMockUser
    @Commit
    @Test
    public void getUserForIdTest() throws Exception {
        String msg = "getUserForIdTest: ";
        User user = findOneUser();
        MvcResult result = this.mockMvc.perform(get("/user/"+user.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/id"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);

    }

    @WithAnonymousUser
    @Commit
    @Test
    public void getUserForScreeNameTest() throws Exception {
        String msg = "getUserForScreeNameTest: ";
        User user = findOneUser();
        MvcResult result = this.mockMvc.perform(get("/user/screenName/"+ user.getScreenName()))
            .andExpect(status().isOk())
            .andExpect(view().name("user/id"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithAnonymousUser
    @Commit
    @Test
    public void getTweetingUsersTest() throws Exception {
        String msg = "getTweetingUsersTest: ";
        MvcResult result = this.mockMvc.perform(get("/user/list/tweets"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/list/allWithTweets"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void getNotYetFriendUsersTest() throws Exception {
        String msg = "getNotYetFriendUsersTest: ";
        MvcResult result = this.mockMvc.perform(get("/user/list/notyetfriends"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/list/friendsNotYet"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void getFriendUsersTest() throws Exception {
        String msg = "getFriendUsersTest: ";

        MvcResult result = this.mockMvc.perform(get("/user/list/friends"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list/friends"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);

    }

    @WithMockUser
    @Commit
    @Test
    public void getFollowerTest() throws Exception {
        String msg = "getFollowerTest: ";

        MvcResult result = this.mockMvc.perform(get("/user/list/follower"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list/follower"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);

    }

    @WithMockUser
    @Commit
    @Test
    public void getNotYetFollowerTest() throws Exception {
        String msg = "getNotYetFollowerTest: ";

        MvcResult result = this.mockMvc.perform(get("/user/list/notyetfollower"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list/followerNotYet"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void getOnListTest() throws Exception {
        String msg = "getOnListTest: ";
        MvcResult result = this.mockMvc.perform(get("/user/list/onlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list/onlist"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void getNotYetOnListTest() throws Exception {
        String msg = "getNotYetOnListTest: ";
        MvcResult result = this.mockMvc.perform(get("/user/list/notyetonlist"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/list/onlistNotYet"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        log.info(msg+content);
        log.info(msg+"#######################################");
        log.info(msg+"#######################################");
        Assert.assertTrue(true);
    }


}
