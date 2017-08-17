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
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.HashTagService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

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
    private HashTagService hashTagService;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void setupTestData(){
        String msg = "setupTestData: ";
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void getAllTest() throws Exception {
        String msg ="getAllTest: ";
        MvcResult result = this.mockMvc.perform(get("/hashtag/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/all"))
                .andExpect(model().attributeExists("myPageContent"))
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

    private HashTag findOneHashTag(){
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, 1);
        Page<HashTag> hashTagPage = hashTagService.getAll(pageRequest);
        if(hashTagPage.getContent().size()>0){
            return hashTagPage.getContent().iterator().next();
        } else {
            return null;
        }
    }

    @Commit
    @WithAnonymousUser
    @Test
    public void findByIdTest() throws Exception {
        String msg ="findByIdTest: ";
        HashTag hashTag = findOneHashTag();
        long id  = hashTag.getId();
        MvcResult result = this.mockMvc.perform(get("/hashtag/"+id))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/id"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("hashTag"))
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

    @Commit
    @WithAnonymousUser
    @Test
    public void hashTagFromTweetsAndUsersTest() throws Exception {
        String msg ="hashTagFromTweetsAndUsersTest: ";
        HashTag hashTag = findOneHashTag();
        String hashtagText = hashTag.getText();
        MvcResult result = this.mockMvc.perform(get("/hashtag/text/"+hashtagText))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/id"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("hashTag"))
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

    @Commit
    @WithAnonymousUser
    @Test
    public void hashTagsOverview()  throws Exception {
        String msg ="hashTagsOverview: ";
        MvcResult result = this.mockMvc.perform(get("/hashtag/overview"))
            .andExpect(status().isOk())
            .andExpect(view().name("hashtag/overview"))
            .andExpect(model().attributeExists("hashTagsTweets"))
            .andExpect(model().attributeExists("hashTagsUsers"))
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
