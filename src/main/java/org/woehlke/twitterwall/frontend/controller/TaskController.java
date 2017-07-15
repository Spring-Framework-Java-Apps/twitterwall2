package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.TaskService;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/task")
public class TaskController  extends AbstractTwitterwallController {

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page, Model model) {
        String msg = "/task/all: ";
        logEnv();
        String title = "Tasks";
        String subtitle = "List aller Tasks";
        String symbol = Symbols.TASK.toString();
        model = setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<Task> allTasks = taskService.getAll(pageRequest);
        model.addAttribute("tasks",allTasks);
        return "task/taskAll";
    }

    @RequestMapping(path="/{id}")
    public String getTaskById(
        @RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page,
        @PathVariable long id, Model model) {
        String msg = "/task/ "+id;
        logEnv();
        String title = "Tasks";
        String subtitle = "List aller TasksHistory für Task";
        String symbol = Symbols.TASK.toString();
        model = setupPage(model,title,subtitle,symbol);
        Task oneTask = taskService.findById(id);
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<TaskHistory> taskHistoryList = taskHistoryService.findByTask(oneTask,pageRequest);
        model.addAttribute("task",oneTask);
        model.addAttribute("taskHistoryList",taskHistoryList);
        return "task/taskHistory";
    }

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.context.test}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

    @Autowired
    public TaskController(TaskService taskService, TaskHistoryService taskHistoryService) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
