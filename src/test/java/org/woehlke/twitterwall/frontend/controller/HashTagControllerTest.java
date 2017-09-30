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
import org.woehlke.twitterwall.*;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.model.HashTag;
import org.woehlke.twitterwall.oodm.service.HashTagService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 01.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test001controllerIsPresentTest(){
        String msg = "test001controllerIsPresentTest ";
        log.debug(msg+"------------------------------------");
        assertThat(controller).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(hashTagService).isNotNull();
        assertThat(prepareDataTest).isNotNull();
        log.debug(msg+"------------------------------------");
    }

    @Commit
    @Test
    public void test002setupTestData(){
        String msg = "test002setupTestData: ";
        log.debug(msg+"------------------------------------");
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

    @Commit
    @WithMockUser
    @Test
    public void test003getAllTest() throws Exception {
        String msg ="test003getAllTest: ";
        log.debug(msg+"------------------------------------");
        String url ="/hashtag/all";
        log.info(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/all"))
                .andExpect(model().attributeExists("myPageContent"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

    private HashTag test004findOneHashTag(){
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
    public void test005findHashTagById() throws Exception {
        String msg ="test005findHashTagById: ";
        log.debug(msg+"------------------------------------");
        HashTag hashTag = test004findOneHashTag();
        long id  = hashTag.getId();
        String url ="/hashtag/"+id;
        log.info(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/id"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("hashTag"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

    @Commit
    @WithAnonymousUser
    @Test
    public void test006hashTagFromTweetsAndUsersTest() throws Exception {
        String msg ="test006hashTagFromTweetsAndUsersTest: ";
        log.debug(msg+"------------------------------------");
        HashTag hashTag = test004findOneHashTag();
        String hashtagText = hashTag.getText();
        String url ="/hashtag/text/"+hashtagText;
        log.info(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("hashtag/id"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("latestTweets"))
                .andExpect(model().attributeExists("hashTag"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

    @Commit
    @WithAnonymousUser
    @Test
    public void test007hashTagsOverview()  throws Exception {
        String msg ="test007hashTagsOverview: ";
        log.debug(msg+"------------------------------------");
        String url ="/hashtag/overview";
        log.info(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name("hashtag/overview"))
            .andExpect(model().attributeExists("hashTagsTweets"))
            .andExpect(model().attributeExists("hashTagsUsers"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

}
