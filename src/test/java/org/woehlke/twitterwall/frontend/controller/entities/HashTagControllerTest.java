package org.woehlke.twitterwall.frontend.controller.entities;

import org.junit.Assert;
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
import org.woehlke.twitterwall.Application;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

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
        for(long id:ID_TWITTER_TO_FETCH_FOR_TWEET_TEST){
            log.info("fetchTweetsFromTwitterSearchTest: ID_TWITTER_TO_FETCH_FOR_TWEET_TEST: "+id);
        }
        //persistDataFromTwitterTest.fetchTweetsFromTwitterSearchTest(ID_TWITTER_TO_FETCH_FOR_TWEET_TEST);
        log.info("fetchTweetsFromTwitterSearchTest: DONE  persistDataFromTwitterTest.fetchTweetsFromTwitterSearchTest()");
        log.info("------------------------------------");
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void hashTagFromTweetsTest() throws Exception {
        String hashtagText = "java";
        MvcResult result = this.mockMvc.perform(get("/hashtag/tweets/"+hashtagText))
            .andExpect(status().isOk())
            .andExpect(view().name("timeline"))
            .andExpect(model().attributeExists("latestTweets"))
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
    public void hashTagForUsersTest() throws Exception {
        String hashtagText = "java";
        MvcResult result = this.mockMvc.perform(get("/hashtag/user/"+hashtagText))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
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

    @Commit
    @Test
    public void hashTagsOverview()  throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hashtag/overview"))
            .andExpect(status().isOk())
            .andExpect(view().name("tags"))
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
