package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test001controllerIsPresentTest(){
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void test002setupTestData() throws Exception {
        String msg = "setupTestData: ";
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @WithAnonymousUser
    @Commit
    @Test
    public void test003getLatestTweetsTest() throws Exception {
        String msg ="test003getLatestTweetsTest: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/all";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
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
    public void test004getTweetById() throws Exception {
        String msg ="test003getLatestTweetsTest: ";
        log.debug(msg+"------------------------------------");
        Tweet tweet = findOneTweet();
        String url = "/tweet/"+tweet.getId();
        log.info(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( "tweet/id"))
                .andExpect(model().attributeExists("tweet"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test005getHomeTimeline() throws Exception {
        String msg ="test005getHomeTimeline: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/timeline/home";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test006getUserTimeline() throws Exception {
        String msg ="test006getUserTimeline: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/timeline/user";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test007getMentions() throws Exception {
        String msg ="test007getMentions: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/mentions";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test008getFavorites() throws Exception {
        String msg ="test008getFavorites: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/favorites";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test009getRetweetsOfMe() throws Exception {
        String msg ="test009getRetweetsOfMe: ";
        log.debug(msg+"------------------------------------");
        String url = "/tweet/retweets";
        log.info(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "tweet/all"))
            .andExpect(model().attributeExists("latestTweets"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }
}
