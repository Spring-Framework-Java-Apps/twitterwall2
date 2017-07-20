package org.woehlke.twitterwall.frontend.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.woehlke.twitterwall.Application;
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.parts.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by tw on 13.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TaskControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController controller;

    @Autowired
    private TaskService taskService;

    @Value("${twitterwall.frontend.menuAppName}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.infoWebpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.frontend.contextTest}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.imprintScreenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.imprintSubtitle}")
    private String imprintSubtitle;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;


    @Autowired
    private ConfigTwitterwall configTwitterwall;

    @Commit
    @Test
    public void controllerIsPresentTest(){
        log.info("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void getAllTest()throws Exception {
        MvcResult result = this.mockMvc.perform(get("/task/all"))
            .andExpect(status().isOk())
            .andExpect(view().name( "task/taskAll"))
            .andExpect(model().attributeExists("tasks"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");
        Assert.assertTrue(true);
    }

    @Commit
    @Test
    public void getTaskByIdTest() throws Exception {
        String msg ="getTaskByIdTest: ";
        Task task = taskService.create(msg, TaskType.FETCH_TWEETS_FROM_TWITTER_SEARCH);
        long id = task.getId();
        MvcResult result = this.mockMvc.perform(get("/task/"+id))
            .andExpect(status().isOk())
            .andExpect(view().name( "task/taskHistory"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("taskHistoryList"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.info("#######################################");
        log.info("#######################################");
        log.info(content);
        log.info("#######################################");
        log.info("#######################################");
        Assert.assertTrue(true);
    }
}
