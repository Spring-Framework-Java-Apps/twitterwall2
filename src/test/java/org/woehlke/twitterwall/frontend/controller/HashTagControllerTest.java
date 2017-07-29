package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.*;
import org.woehlke.twitterwall.conf.properties.SchedulerProperties;

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
public class HashTagControllerTest {

    private static final Logger log = LoggerFactory.getLogger(HashTagControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HashTagController controller;

    @Autowired
    private SchedulerProperties schedulerProperties;

    @Commit
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        log.info("fetchTweetsFromTwitterSearchTest: START persistDataFromTwitterTest.fetchTweetsFromTwitterSearchTest()");
        for(long id: schedulerProperties.getFacade().getIdTwitterToFetchForTweetTest()){
            log.info("fetchTweetsFromTwitterSearchTest: ID_TWITTER_TO_FETCH_FOR_TWEET_TEST: "+id);
        }
        //persistDataFromTwitterTest.fetchTweetsFromTwitterSearchTest(ID_TWITTER_TO_FETCH_FOR_TWEET_TEST);
        log.info("fetchTweetsFromTwitterSearchTest: DONE  persistDataFromTwitterTest.fetchTweetsFromTwitterSearchTest()");
        log.info("------------------------------------");
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void getAllTest()throws Exception {
        String hashtagText = "java";
        MvcResult result = this.mockMvc.perform(get("/hashtag/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/all"))
                .andExpect(model().attributeExists("myPageContent"))
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
    public void hashTagFromTweetsAndUsersTest() throws Exception {
        String hashtagText = "java";
        MvcResult result = this.mockMvc.perform(get("/hashtag/"+hashtagText))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/hashtagText"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("hashTag"))
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

    @Ignore
    @Commit
    @Test
    public void hashTagsOverview()  throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hashtag/overview"))
            .andExpect(status().isOk())
            .andExpect(view().name("hashtag/overview"))
            .andExpect(model().attributeExists("hashTagsTweets"))
            .andExpect(model().attributeExists("hashTagsUsers"))
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
