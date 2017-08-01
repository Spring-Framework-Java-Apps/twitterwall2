package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.woehlke.twitterwall.PrepareDataTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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

    private final static String URL_PATH = "/application/countedEntities";
    private final static String TEMPLATE_PATH = "application/countedEntities";


    @WithMockUser
    @Commit
    @Test
    public void domainCountTweet2hashtag() throws Exception {
        String msg ="domainCountTweet2hashtag: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/tweet/hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountTweet2media() throws Exception {
        String msg ="domainCountTweet2media: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/tweet/media"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountTweet2mention() throws Exception {
        String msg ="domainCountTweet2mention: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/tweet/mention"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountTweet2tickersymbol() throws Exception {
        String msg ="domainCountTweet2tickersymbol: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/tweet/tickersymbol"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountTweet2url() throws Exception {
        String msg ="domainCountTweet2url: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/tweet/url"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountUserprofile2hashtag() throws Exception {
        String msg ="domainCountUserprofile2hashtag: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/user/hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountUserprofile2media() throws Exception {
        String msg ="domainCountUserprofile2media: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/user/media"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountUserprofile2mention() throws Exception {
        String msg ="domainCountUserprofile2mention: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/user/mention"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountUserprofile2Tickersymbol() throws Exception {
        String msg ="domainCountUserprofile2Tickersymbol: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/user/tickersymbol"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
    public void domainCountUserprofile2Url() throws Exception {
        String msg ="domainCountUserprofile2Url: ";

        MvcResult result = this.mockMvc.perform(get(URL_PATH+"/user/url"))
                .andExpect(status().isOk())
                .andExpect(view().name( TEMPLATE_PATH ))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("listObject2EntityContent"))
                .andExpect(model().attributeExists("listObject2Entity"))
                .andExpect(model().attributeExists("nameObject"))
                .andExpect(model().attributeExists("nameEntity"))
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
