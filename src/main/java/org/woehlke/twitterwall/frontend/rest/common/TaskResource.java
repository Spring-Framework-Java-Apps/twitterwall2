package org.woehlke.twitterwall.frontend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.application.Task;
import org.woehlke.twitterwall.oodm.entities.application.TaskHistory;
import org.woehlke.twitterwall.oodm.service.application.TaskHistoryService;
import org.woehlke.twitterwall.oodm.service.application.TaskService;

import java.util.List;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping("/rest/task")
public class TaskResource {

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody List<Task> countedEntities(Model model) {
        List<Task> allTasks = taskService.getAll();
        return allTasks;
    }

    @RequestMapping(path="/{id}",method= RequestMethod.GET)
    public @ResponseBody
    List<TaskHistory> countedEntities(@PathVariable long id, Model model) {
        Task oneTask = taskService.findById(id);
        List<TaskHistory> taskHistoryList = taskHistoryService.findByTask(oneTask);
        return taskHistoryList;
    }

    @Autowired
    public TaskResource(TaskService taskService, TaskHistoryService taskHistoryService) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
    }

    private final TaskService taskService;

    private final TaskHistoryService taskHistoryService;
}
