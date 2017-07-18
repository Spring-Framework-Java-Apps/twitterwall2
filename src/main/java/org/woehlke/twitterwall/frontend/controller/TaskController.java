package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.TaskService;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/task")
public class TaskController {

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String msg = "/task/all: ";
        controllerHelper.logEnv();
        String title = "Tasks";
        String subtitle = "List aller Tasks";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC,"timeStarted");
        Page<Task> allTasks = taskService.getAll(pageRequest);
        model.addAttribute("tasks",allTasks);
        return "task/taskAll";
    }

    @RequestMapping(path="/{id}")
    public String getTaskById(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        //@PathVariable long id, Model model) {
        @PathVariable("id") Task task, Model model) {
        String msg = "/task/ "+task.getId();
        controllerHelper.logEnv();
        String title = "Tasks";
        String subtitle = "List aller TasksHistory für Task";
        String symbol = Symbols.TASK.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        //Task oneTask = taskService.findById(id);
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<TaskHistory> taskHistoryList = taskHistoryService.findByTask(task,pageRequest);
        model.addAttribute("task",task);
        model.addAttribute("taskHistoryList",taskHistoryList);
        return "task/taskHistory";
    }

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;


    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    @Autowired
    public TaskController(TaskService taskService, TaskHistoryService taskHistoryService, ControllerHelper controllerHelper) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
