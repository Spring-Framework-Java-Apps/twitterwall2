package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping("/rest/taskhistory")
public class TaskHistoryResource {

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<TaskHistory> countedEntities(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<TaskHistory> allTasks = taskHistoryService.getAll(pageRequest);
        return allTasks;
    }

    @RequestMapping(path="/{id}",method= RequestMethod.GET)
    public @ResponseBody
    TaskHistory countedEntities(@PathVariable long id, Model model) {
        TaskHistory taskHistory = taskHistoryService.findById(id);
        return taskHistory;
    }

    @Autowired
    public TaskHistoryResource(TaskHistoryService taskHistoryService, TwitterwallFrontendProperties twitterwallFrontendProperties) {
        this.taskHistoryService = taskHistoryService;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
    }

    private final TaskHistoryService taskHistoryService;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;
}
