package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

import static org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping("/rest/taskhistory")
public class TaskHistoryResource {

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<TaskHistory> countedEntities(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page, Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<TaskHistory> allTasks = taskHistoryService.getAll(pageRequest);
        return allTasks;
    }

    @RequestMapping(path="/{id}",method= RequestMethod.GET)
    public @ResponseBody
    TaskHistory countedEntities(@PathVariable long id, Model model) {
        TaskHistory taskHistory = taskHistoryService.findById(id);
        return taskHistory;
    }


    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    @Autowired
    public TaskHistoryResource(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    private final TaskHistoryService taskHistoryService;
}
