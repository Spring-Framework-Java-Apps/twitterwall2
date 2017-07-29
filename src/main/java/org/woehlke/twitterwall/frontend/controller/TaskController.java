package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.mq.endoint.AsyncStartTask;
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/task")
public class TaskController {

    @RequestMapping(path="/all")
    public String getAll(
            @RequestParam(name= "page",defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        String msg = "/task/all: ";
        String title = "Tasks";
        String subtitle = "List aller Tasks";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
                page, frontendProperties.getPageSize(),
                Sort.Direction.DESC,
                "timeStarted"
        );
        Page<Task> allTasks = taskService.getAll(pageRequest);
        model.addAttribute("tasks",allTasks);
        return PATH+"/all";
    }

    @RequestMapping(path="/{id}")
    public String getTaskById(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        @PathVariable("id") Task task, Model model) {
        String msg = "/task/ "+task.getId();
        String title = "Task "+task.getUniqueId();
        String subtitle = "List of TasksHistory for Task";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<TaskHistory> taskHistoryList = taskHistoryService.findByTask(task,pageRequest);
        model.addAttribute("task",task);
        model.addAttribute("taskHistoryList",taskHistoryList);
        return PATH+"/id";
    }

    @RequestMapping("/start/createTestData")
    public String getTestData(Model model) {
        model = controllerHelper.setupPage(
                model,"Test Data Tweets",
                twitterProperties.getSearchQuery(),
                Symbols.GET_TEST_DATA.toString()
        );
        String msg = PATH+"/start/createTestData: ";
        if(frontendProperties.getContextTest()){
            model.addAttribute("latestTweets", mqStartTask.createTestDataForTweets());
            model.addAttribute("users", mqStartTask.createTestDataForUser());
        } else {
            model.addAttribute("latestTweets",null);
            model.addAttribute("users",null);
        }
        return PATH+"/start/createTestData";
    }

    @RequestMapping("/start/user/onlist/renew")
    public String getOnListRenew(
            @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        String msg = "getOnListRenew: ";
        log.info(msg+"START startTask.fetchUsersFromDefinedUserList: ");
        mqAsyncStartTask.fetchUsersFromDefinedUserList();
        log.info(msg+"DONE startTask.fetchUsersFromDefinedUserList: ");
        log.info(msg+"START userService.findOnList(): ");
        Page<User> usersOnList = userService.getOnList(pageRequest);
        log.info(msg+"DONE userService.findOnList(): ");
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "Renew List of Users On List";
        model = controllerHelper.setupPage(model, title, "Users", symbol);
        return PATH+"/start/renew";
    }

    @RequestMapping(path="/start/tweets/search")
    public String  fetchTweetsFromTwitterSearchStartTask(Model model) {
        String msg = "/start/tweets/search";
        String title = "Scheduled Task started";
        String subtitle = "/start/tweets/search";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.fetchTweetsFromTwitterSearch();
        return PATH+"/start/taskStarted";
    }

    @RequestMapping(path="/start/tweets/update")
    public String updateTweetsStartTask(Model model) {
        String msg = "/start/tweets/fetch";
        String title = "Scheduled Task started";
        String subtitle = "/start/tweets/update";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateTweets();
        return PATH+"/start/taskStarted";
    }

    @RequestMapping(path="/start/users/list/fetch")
    public String fetchUsersFromDefinedUserListStartTask(Model model){
        String msg = "/start/users/list/fetch";
        String title = "Scheduled Task started";
        String subtitle = "/start/users/list/fetch";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.fetchUsersFromDefinedUserList();
        return PATH+"/start/taskStarted";
    }

    @RequestMapping(path="/start/users/mentions/update")
    public String updateUserProfilesFromMentionsStartTask(Model model){
        String msg = "/start/users/mentions/update";
        String title = "Scheduled Task started";
        String subtitle = "/start/users/mentions/update";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateUserProfilesFromMentions();
        return PATH+"/start/taskStarted";
    }

    @RequestMapping(path="/start/users/update")
    public String updateUserProfilesStartTask(Model model) {
        String msg = "/start/users/update";
        String title = "Scheduled Task started";
        String subtitle = "/start/users/update";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateUserProfiles();
        return PATH+"/start/taskStarted";
    }

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final String PATH = "task";

    private final UserService userService;

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;

    private final FrontendProperties frontendProperties;

    private final ControllerHelper controllerHelper;

    private final AsyncStartTask mqAsyncStartTask;

    private final TwitterProperties twitterProperties;

    private final StartTask mqStartTask;


    @Autowired
    public TaskController(
            UserService userService, TaskService taskService,
            TaskHistoryService taskHistoryService,
            FrontendProperties frontendProperties,
            ControllerHelper controllerHelper,
            AsyncStartTask mqAsyncStartTask,
            TwitterProperties twitterProperties, StartTask mqStartTask) {
        this.userService = userService;
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
        this.frontendProperties = frontendProperties;
        this.controllerHelper = controllerHelper;
        this.mqAsyncStartTask = mqAsyncStartTask;
        this.twitterProperties = twitterProperties;
        this.mqStartTask = mqStartTask;
    }

}
