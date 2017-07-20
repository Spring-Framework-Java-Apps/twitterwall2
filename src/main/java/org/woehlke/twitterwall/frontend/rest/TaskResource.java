package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.model.TaskResourceModel;
import org.woehlke.twitterwall.oodm.entities.Task;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.TaskService;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping("/rest/task")
public class TaskResource {

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody Page<Task> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        Pageable pageRequest = new PageRequest(page, configTwitterwall.getFrontend().getPageSize());
        Page<Task> allTasks = taskService.getAll(pageRequest);
        return allTasks;
    }

    @RequestMapping(path="/{id}", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    TaskResourceModel findByTaskId(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
                                   @PathVariable long id, Model model) {
        Task oneTask = taskService.findById(id);
        Pageable pageRequest = new PageRequest(page, configTwitterwall.getFrontend().getPageSize());
        Page<TaskHistory> taskHistoryList = taskHistoryService.findByTask(oneTask,pageRequest);
        TaskResourceModel taskResourceModel = new TaskResourceModel(oneTask,taskHistoryList);
        return taskResourceModel;
    }


    //@Value("${twitterwall.frontend.maxResults}")
    //private int pageSize;

    @Autowired
    public TaskResource(ConfigTwitterwall configTwitterwall, TaskService taskService, TaskHistoryService taskHistoryService) {
        this.configTwitterwall = configTwitterwall;
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
    }

    private final ConfigTwitterwall configTwitterwall;

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;
}
