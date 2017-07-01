package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.test.UserServiceTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by tw on 19.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
public class ImprintControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ImprintControllerTest.class);
    
    @Autowired
    private ImprintController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceTest userServiceTest;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;
    
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void fetchTweetsFromTwitterSearchTest() {
        log.info("------------------------------------");
        log.info("fetchTweetsFromTwitterSearchTest: START  userServiceTest.createUser("+imprintScreenName+")");
        userServiceTest.createUser(imprintScreenName);
        log.info("fetchTweetsFromTwitterSearchTest: DONE  userServiceTest.createUser("+imprintScreenName+")");
        Assert.assertTrue(true);
        log.info("------------------------------------");
    }
    
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/imprint")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("port80guru")));
    }
}
