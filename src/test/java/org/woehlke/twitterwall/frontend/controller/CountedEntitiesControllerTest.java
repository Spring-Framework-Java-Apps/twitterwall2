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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountedEntitiesControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CountedEntitiesControllerTest.class);

    @Autowired
    private CountedEntitiesController controller;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Autowired
    private MockMvc mockMvc;

    @Commit
    @Test
    public void test001controllerIsPresentTest(){
        String msg = "controllerIsPresentTest: ";
        log.debug(msg+"------------------------------------");
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
        log.debug(msg+"------------------------------------");
    }

    @Commit
    @Test
    public void test002setupTestData() throws Exception {
        String msg = "setupTestData: ";
        log.debug(msg+"------------------------------------");
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
        log.debug(msg+"------------------------------------");
    }

    private final static String URL_PATH = "/application/countedEntities";
    private final static String TEMPLATE_PATH = "application/countedEntities";

    @WithMockUser
    @Commit
    @Test
    public void test003domainCountTest() throws Exception {
        String msg ="domainCountTest: ";
        log.debug(msg+"------------------------------------");
        String url = "/application/countedEntities/domain/count";
        log.debug(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(view().name( "application/domain/count"))
            .andExpect(model().attributeExists("countedEntities"))
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

    @WithMockUser
    @Commit
    @Test
    public void test004domainCountTweet2hashtag() throws Exception {
        String msg ="domainCountTweet2hashtag: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/tweet/hashtag";
        log.debug(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test005domainCountTweet2media() throws Exception {
        String msg ="domainCountTweet2media: ";
        log.debug(msg+"------------------------------------");
        String url = URL_PATH+"/tweet/media";
        log.debug(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test006domainCountTweet2mention() throws Exception {
        String msg ="domainCountTweet2mention: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/tweet/mention";
        log.debug(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test007domainCountTweet2tickersymbol() throws Exception {
        String msg ="domainCountTweet2tickersymbol: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/tweet/tickersymbol";
        log.debug(msg+url);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test008domainCountTweet2url() throws Exception {
        String msg ="domainCountTweet2url: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/tweet/url";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test009domainCountUserprofile2hashtag() throws Exception {
        String msg ="domainCountUserprofile2hashtag: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/user/hashtag";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test010domainCountUserprofile2media() throws Exception {
        String msg ="domainCountUserprofile2media: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/user/media";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test011domainCountUserprofile2mention() throws Exception {
        String msg ="domainCountUserprofile2mention: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/user/mention";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test012domainCountUserprofile2Tickersymbol() throws Exception {
        String msg ="domainCountUserprofile2Tickersymbol: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/user/tickersymbol";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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

    @WithMockUser
    @Commit
    @Test
    public void test013domainCountUserprofile2Url() throws Exception {
        String msg ="domainCountUserprofile2Url: ";
        log.debug(msg+"------------------------------------");
        String url =URL_PATH+"/user/url";
        log.debug(msg+url);

        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
