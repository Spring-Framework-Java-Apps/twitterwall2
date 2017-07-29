package org.woehlke.twitterwall.frontend.controller;

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
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.TaskService;
import org.woehlke.twitterwall.scheduled.mq.endoint.AsyncStartTask;

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

    @RequestMapping(path="/scheduled/tweets/fetch")
    public String  fetchTweetsFromTwitterSearchStartTask(Model model) {
        String msg = "/scheduled/tweets/fetch";
        String title = "Scheduled Task started";
        String subtitle = "/scheduled/tweets/fetch";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.fetchTweetsFromTwitterSearch();
        return PATH+"scheduled/taskStarted";
    }

    @RequestMapping(path="/scheduled/tweets/update")
    public String updateTweetsStartTask(Model model) {
        String msg = "/scheduled/tweets/fetch";
        String title = "Scheduled Task started";
        String subtitle = "/scheduled/tweets/fetch";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateTweets();
        return PATH+"scheduled/taskStarted";
    }

    @RequestMapping(path="/scheduled/users/list/fetch")
    public String fetchUsersFromDefinedUserListStartTask(Model model){
        String msg = "/scheduled/users/list/fetch";
        String title = "Scheduled Task started";
        String subtitle = "/scheduled/users/list/fetch";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.fetchUsersFromDefinedUserList();
        return PATH+"scheduled/taskStarted";
    }

    @RequestMapping(path="/scheduled/users/mentions/update")
    public String updateUserProfilesFromMentionsStartTask(Model model){
        String msg = "/scheduled/users/mentions/update";
        String title = "Scheduled Task started";
        String subtitle = "/scheduled/users/mentions/update";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateUserProfilesFromMentions();
        return PATH+"scheduled/taskStarted";
    }

    @RequestMapping(path="/scheduled/users/update")
    public String updateUserProfilesStartTask(Model model) {
        String msg = "/scheduled/users/update";
        String title = "Scheduled Task started";
        String subtitle = "/scheduled/users/update";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        mqAsyncStartTask.updateUserProfiles();
        return PATH+"scheduled/taskStarted";
    }

    private final String PATH = "/task";

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;

    private final FrontendProperties frontendProperties;

    private final ControllerHelper controllerHelper;

    private final AsyncStartTask mqAsyncStartTask;

    @Autowired
    public TaskController(
            TaskService taskService,
            TaskHistoryService taskHistoryService,
            FrontendProperties frontendProperties,
            ControllerHelper controllerHelper,
            AsyncStartTask mqAsyncStartTask
    ) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
        this.frontendProperties = frontendProperties;
        this.controllerHelper = controllerHelper;
        this.mqAsyncStartTask = mqAsyncStartTask;
    }

}
