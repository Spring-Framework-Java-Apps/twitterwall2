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
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TweetControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TweetControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TweetController controller;

    @Autowired
    private TweetService tweetService;

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

    @WithAnonymousUser
    @Commit
    @Test
    public void getLatestTweetsTest() throws Exception {
        String msg ="getLatestTweetsTest: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/all"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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

    private Tweet findOneTweet(){
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, 1);
        Page<Tweet> tweetPage = tweetService.getAll(pageRequest);
        if(tweetPage.getContent().size()>0){
            return tweetPage.getContent().iterator().next();
        } else {
            return null;
        }
    }

    @WithMockUser
    @Commit
    @Test
    public void getTweetById() throws Exception {
        String msg ="getLatestTweetsById: ";
        Tweet tweet = findOneTweet();

        MvcResult result = this.mockMvc.perform(get("/tweet/"+tweet.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name( "tweet/id"))
                .andExpect(model().attributeExists("tweet"))
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
    public void getHomeTimeline() throws Exception {
        String msg ="getHomeTimeline: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/timeline/home"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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

    @WithMockUser
    @Commit
    @Test
    public void getUserTimeline() throws Exception {
        String msg ="getUserTimeline: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/timeline/user"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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

    @WithMockUser
    @Commit
    @Test
    public void getMentions() throws Exception {
        String msg ="getMentions: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/mentions"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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

    @WithMockUser
    @Commit
    @Test
    public void getFavorites() throws Exception {
        String msg ="getFavorites: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/favorites"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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

    @WithMockUser
    @Commit
    @Test
    public void getRetweetsOfMe() throws Exception {
        String msg ="getRetweetsOfMe: ";

        MvcResult result = this.mockMvc.perform(get("/tweet/retweets"))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
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
}
