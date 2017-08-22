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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.service.UrlService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UrlControllerTest.class);

    @Autowired
    private UrlController controller;

    @Autowired
    private UrlService urlService;

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

    @WithMockUser
    @Commit
    @Test
    public void getAllTest() throws Exception {
        String msg ="getAllTest: ";
        MvcResult result = this.mockMvc.perform(get("/url/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("url/all"))
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

    @Commit
    @WithMockUser
    @Test
    public void getUrlById() throws Exception {
        String msg ="getUrlById: ";
        Url oneUrl = findOneUrl();
        if(oneUrl != null) {
            long id = oneUrl.getId();
            MvcResult result = this.mockMvc.perform(get("/url/" + id))
                    .andExpect(status().isOk())
                    .andExpect(view().name("url/id"))
                    .andExpect(model().attributeExists("users"))
                    .andExpect(model().attributeExists("latestTweets"))
                    .andExpect(model().attributeExists("url"))
                    .andExpect(model().attributeExists("page"))
                    .andReturn();

            String content = result.getResponse().getContentAsString();

            log.info(msg + "#######################################");
            log.info(msg + "#######################################");
            log.info(msg + content);
            log.info(msg + "#######################################");
            log.info(msg + "#######################################");
            Assert.assertTrue(true);
        }
    }

    private Url findOneUrl() {
        int size = 1;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, size);
        Page<Url> urlPage = urlService.getAll(pageRequest);
        if(urlPage.getContent().size()>0){
            return urlPage.getContent().iterator().next();
        } else {
            return null;
        }
    }

}
