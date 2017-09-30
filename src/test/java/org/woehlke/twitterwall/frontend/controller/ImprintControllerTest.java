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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.PrepareDataTest;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImprintControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ImprintControllerTest.class);

    @Autowired
    private ImprintController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FrontendProperties frontendProperties;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Test
    public void test001controllerIsPresentTest(){
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void test002prepareDataTest() throws Exception  {
        log.debug("------------------------------------");
        log.debug("fetchTweetsFromSearchTest: START  userServiceTest.createUser("+ frontendProperties.getImprintScreenName()+")");
        prepareDataTest.createUser(frontendProperties.getImprintScreenName());
        log.debug("fetchTweetsFromSearchTest: DONE  userServiceTest.createUser("+ frontendProperties.getImprintScreenName()+")");
        log.debug("------------------------------------");
        Assert.assertTrue(true);
    }

    @Commit
    @WithAnonymousUser
    @Test
    public void test003imprintTest1() throws Exception {
        this.mockMvc.perform(get("/imprint")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("port80guru")));
        Assert.assertTrue(true);
    }

    @Commit
    @WithAnonymousUser
    @Test
    public void test004imprintTest2() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/imprint"))
                .andExpect(status().isOk())
                .andExpect(view().name( "imprint/imprint"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("page")).andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug("#######################################");
        log.debug("#######################################");
        log.debug(content);
        log.debug("#######################################");
        log.debug("#######################################");
        Assert.assertTrue(true);
    }
}
