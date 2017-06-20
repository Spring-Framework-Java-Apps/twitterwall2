package org.woehlke.twitterwall.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.helper.TestHelperService;

import javax.transaction.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DataJpaTest(showSql=false)
@Transactional(Transactional.TxType.NOT_SUPPORTED)
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
public class ProfileControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ProfileControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProfileController controller;

    @Autowired
    private TestHelperService testHelperService;

    private static long idTwitterToFetch[] = {
            876433563561938944L, // t3c_berlin
            876456051016597504L, // Codemonkey1988
            876420491329892354L, // Walter_kran
            876425220147564544L, // tobenschmidt
            876819177746649088L, // Oliver1973
            876514968933478400L, // wowa_TYPO3
            876441863191920641L, // dirscherl17
            876441015523192832L, // Markus306
            876440419416109056L  // mattLefaux
    };

    @Test
    public void controllerIsPresentTest() throws Exception {
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        testHelperService.fetchTweetsFromTwitterSearchTest(idTwitterToFetch);
        Assert.assertTrue(true);
    }

    //@Ignore
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        //this.mockMvc.perform(get("/profile/t3c_berlin")).andDo(print()).andExpect(status().isOk())
        //        .andExpect(content().string(containsString("t3c_berlin")));

        MvcResult result = this.mockMvc.perform(get("/profile/wowa_TYPO3"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("page")).andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");

    }
}
