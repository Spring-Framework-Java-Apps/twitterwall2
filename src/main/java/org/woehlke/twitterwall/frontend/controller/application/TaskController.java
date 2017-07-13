package org.woehlke.twitterwall.frontend.controller.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.service.application.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.application.TaskService;

import java.util.List;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/task")
public class TaskController  extends AbstractTwitterwallController {

    @RequestMapping(path="/all")
    public String getAll(Model model) {
        String msg = "/task/all: ";
        logEnv();
        String title = "Tasks";
        String subtitle = "List aller Tasks";
        String symbol = Symbols.TASK.toString();
        model = setupPage(model,title,subtitle,symbol);
        List<Task> allTasks = taskService.getAll();
        model.addAttribute("tasks",allTasks);
        return "taskAll";
    }

    @RequestMapping(path="/{id}")
    public String getTaskById(@PathVariable long id, Model model) {
        String msg = "/task/ "+id;
        logEnv();
        String title = "Tasks";
        String subtitle = "List aller TasksHistory für Task";
        String symbol = Symbols.TASK.toString();
        model = setupPage(model,title,subtitle,symbol);
        Task oneTask = taskService.findById(id);
        List<TaskHistory> taskHistoryList = taskHistoryService.findByTask(oneTask);
        model.addAttribute("task",oneTask);
        model.addAttribute("taskHistoryList",taskHistoryList);
        return "taskHistory";
    }

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;

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
