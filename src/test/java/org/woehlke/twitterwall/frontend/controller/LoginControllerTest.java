package org.woehlke.twitterwall.frontend.controller;


import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import org.woehlke.twitterwall.Application;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginControllerTest {

    private static final Logger log = LoggerFactory.getLogger(LoginControllerTest.class);

    @Autowired
    private LoginController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test001controllerIsPresentTest(){
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }


    //TODO: #218 https://github.com/phasenraum2010/twitterwall2/issues/218
    @Commit
    @Ignore
    @WithAnonymousUser
    @Test
    public void test002login() throws Exception {
        boolean ok = true;
        assertThat(ok).isTrue();
    }
}
