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
public class ApplicationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ApplicationControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationController controller;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Commit
    @Test
    public void test001controllerIsPresentTest(){
        String msg = "fetchTransientUrlsTest: ";
        log.debug(msg+"------------------------------------");
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(prepareDataTest).isNotNull();
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

    @WithMockUser
    @Commit
    @Test
    public void test003managementPageTest() throws Exception {
        String msg ="managementPageTest: ";
        log.debug(msg+"------------------------------------");
        MvcResult result = this.mockMvc.perform(get("/application/management"))
                .andExpect(status().isOk())
                .andExpect(view().name( "application/management"))
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
