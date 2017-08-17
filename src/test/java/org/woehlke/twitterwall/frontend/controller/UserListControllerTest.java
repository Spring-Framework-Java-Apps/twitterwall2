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
import org.woehlke.twitterwall.oodm.service.UserListService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserListControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserListControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserListController controller;

    @Autowired
    private UserListService userListService;


    @Commit
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @WithMockUser
    @Commit
    @Test
    public void getAllTest() throws Exception {
        String msg = "getAllTest: ";
        MvcResult result = this.mockMvc.perform(get("/userlist/all"))
            .andExpect(status().isOk())
            .andExpect(view().name("userlist/all"))
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

    //TODO: #252 https://github.com/phasenraum2010/twitterwall2/issues/252
    @WithMockUser
    @Commit
    @Test
    public void getUserListForIdTest() throws Exception {
        Assert.assertTrue(true);
    }


}
