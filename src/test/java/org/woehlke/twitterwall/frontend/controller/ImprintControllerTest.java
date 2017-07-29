package org.woehlke.twitterwall.frontend.controller;

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
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.test.UserServiceTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ImprintControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ImprintControllerTest.class);

    @Autowired
    private ImprintController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceTest userServiceTest;

    @Autowired
    private TwitterProperties twitterProperties;

    @Autowired
    private FrontendProperties frontendProperties;

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
        log.info("fetchTweetsFromTwitterSearchTest: START  userServiceTest.createUser("+ frontendProperties.getImprintScreenName()+")");
        userServiceTest.createUser(frontendProperties.getImprintScreenName());
        log.info("fetchTweetsFromTwitterSearchTest: DONE  userServiceTest.createUser("+ frontendProperties.getImprintScreenName()+")");
        log.info("------------------------------------");
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void imprintTest1() throws Exception {
        this.mockMvc.perform(get("/imprint")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("port80guru")));
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void imprintTest2() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/imprint"))
                .andExpect(status().isOk())
                .andExpect(view().name( "imprint"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("page")).andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");
        Assert.assertTrue(true);
    }
}