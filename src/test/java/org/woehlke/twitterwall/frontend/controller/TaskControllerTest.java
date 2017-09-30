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
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.parts.CountedEntities;
import org.woehlke.twitterwall.oodm.model.tasks.TaskType;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.CountedEntitiesService;
import org.woehlke.twitterwall.oodm.model.tasks.TaskSendType;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TaskControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController controller;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PrepareDataTest prepareDataTest;

    @Autowired
    private CountedEntitiesService countedEntitiesService;

    @Test
    public void test001controllerIsPresentTest(){
        log.debug("controllerIsPresentTest");
        assertThat(controller).isNotNull();
    }

    @Commit
    @Test
    public void test002setupTestData() throws Exception {
        String msg = "setupTestData: ";
        prepareDataTest.getTestDataTweets(msg);
        prepareDataTest.getTestDataUser(msg);
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void test003getAllTest()throws Exception {
        MvcResult result = this.mockMvc.perform(get("/task/all"))
            .andExpect(status().isOk())
            .andExpect(view().name( "task/all"))
            .andExpect(model().attributeExists("tasks"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug("#######################################");
        log.debug("#######################################");
        log.debug(content);
        log.debug("#######################################");
        log.debug("#######################################");
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void test004getTaskByIdTest() throws Exception {
        CountedEntities countedEntities = countedEntitiesService.countAll();
        String msg ="getTaskByIdTest: ";
        TaskType taskType = TaskType.FETCH_TWEETS_FROM_SEARCH;
        TaskSendType taskSendType = TaskSendType.NO_MQ;
        Task task = taskService.create(msg,taskType, taskSendType,countedEntities);
        long id = task.getId();
        MvcResult result = this.mockMvc.perform(get("/task/"+id))
            .andExpect(status().isOk())
            .andExpect(view().name( "task/id"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("taskHistoryList"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug("#######################################");
        log.debug("#######################################");
        log.debug(content);
        log.debug("#######################################");
        log.debug("#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test005createTestDataTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/task/start/createTestData"))
                .andExpect(status().isOk())
                .andExpect(view().name( "task/start/createTestData"))
                .andExpect(model().attributeExists("taskTweets"))
                .andExpect(model().attributeExists("taskUsers"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug("#######################################");
        log.debug("#######################################");
        log.debug(content);
        log.debug("#######################################");
        log.debug("#######################################");
        Assert.assertTrue(true);
    }

    @Commit
    @WithMockUser
    @Test
    public void test006getOnListRenewTest() throws Exception {
        String msg = "getOnListRenewTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/user/onlist/renew"))
                .andExpect(status().isOk())
                .andExpect(view().name( "task/start/renew"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    private final String PATH = "task";

    @WithMockUser
    @Commit
    @Test
    public void test007fetchTweetsFromTwitterSearchStartTaskTest() throws Exception {
        String msg = "fetchTweetsFromTwitterSearchStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/search"))
                .andExpect(status().isOk())
                .andExpect(view().name( PATH+"/start/taskStarted"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test008fetchFollowerStartTaskTest() throws Exception {
        String msg = "fetchFollowerStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/users/follower/fetch"))
                .andExpect(status().isOk())
                .andExpect(view().name( PATH+"/start/taskStarted"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test009fetchFriendsStartTask() throws Exception {
    String msg = "fetchFriendsStartTask: ";
    MvcResult result = this.mockMvc.perform(get("/task/start/users/friends/fetch"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test010updateTweetsStartTaskTest() throws Exception {
        String msg = "updateTweetsStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/user/onlist/renew"))
                .andExpect(status().isOk())
                .andExpect(view().name( "task/start/renew"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test011fetchUsersFromDefinedUserListStartTaskTest() throws Exception {
        String msg = "fetchUsersFromDefinedUserListStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/users/list/fetch"))
                .andExpect(status().isOk())
                .andExpect(view().name( PATH+"/start/taskStarted"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test012updateUserProfilesFromMentionsStartTaskTest() throws Exception {
        String msg = "updateUserProfilesFromMentionsStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/users/list/fetch"))
                .andExpect(status().isOk())
                .andExpect(view().name( PATH+"/start/taskStarted"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test013updateUserProfilesStartTaskTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/users/mentions/update"))
                .andExpect(status().isOk())
                .andExpect(view().name( PATH+"/start/taskStarted"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("page"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test014getHomeTimelineTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/timeline/home"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test015getUserTimelineTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/timeline/user"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test016getMentionsTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/mentions"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test017getFavoritesTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/favorites"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test018getRetweetsOfMeTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/tweets/myretweets"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/taskStarted"))
            .andExpect(model().attributeExists("task"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

    @WithMockUser
    @Commit
    @Test
    public void test019getListsTest() throws Exception {
        String msg = "updateUserProfilesStartTaskTest: ";
        MvcResult result = this.mockMvc.perform(get("/task/start/userlists"))
            .andExpect(status().isOk())
            .andExpect(view().name( PATH+"/start/tasksStarted"))
            .andExpect(model().attributeExists("listOfTasks"))
            .andExpect(model().attributeExists("page"))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        log.debug(msg+content);
        log.debug(msg+"#######################################");
        log.debug(msg+"#######################################");
        Assert.assertTrue(true);
    }

}
