package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
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
    public @ResponseBody Page<TaskHistory> countedEntities(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page
    ) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<TaskHistory> allTasks = taskHistoryService.getAll(pageRequest);
        return allTasks;
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody TaskHistory findById(
        @PathVariable("id") TaskHistory taskHistory
    ) {
        return taskHistory;
    }

    private final TaskHistoryService taskHistoryService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public TaskHistoryResource(
            TaskHistoryService taskHistoryService,
            FrontendProperties frontendProperties
    ) {
        this.taskHistoryService = taskHistoryService;
        this.frontendProperties = frontendProperties;
    }
}
