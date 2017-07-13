package org.woehlke.twitterwall.frontend.rest.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.service.application.TaskHistoryService;

import java.util.List;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping("/rest/taskhistory")
public class TaskHistoryResource {

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody
    List<TaskHistory> countedEntities(Model model) {
        List<TaskHistory> allTasks = taskHistoryService.getAll();
        return allTasks;
    }

    @RequestMapping(path="/{id}",method= RequestMethod.GET)
    public @ResponseBody
    TaskHistory countedEntities(@PathVariable long id, Model model) {
        TaskHistory taskHistory = taskHistoryService.findById(id);
        return taskHistory;
    }

    @Autowired
    public TaskHistoryResource(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    private final TaskHistoryService taskHistoryService;
}
