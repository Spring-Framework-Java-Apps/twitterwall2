package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/taskhistory")
public class TaskHistoryController {

    private final static String PATH="/taskhistory";

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        controllerHelper.logEnv();
        String subtitle = "all";
        String title = "TaskHistory";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, configTwitterwall.getFrontend().getPageSize(), Sort.Direction.DESC,"timeEvent");
        Page<TaskHistory> myPageContent = taskHistoryService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return PATH+"/all";
    }

    private final TaskHistoryService taskHistoryService;

    //@Value("${twitterwall.frontend.maxResults}")
    //private int pageSize;

    private final ConfigTwitterwall configTwitterwall;

    @Autowired
    public TaskHistoryController(TaskHistoryService taskHistoryService, ConfigTwitterwall configTwitterwall, ControllerHelper controllerHelper) {
        this.taskHistoryService = taskHistoryService;
        this.configTwitterwall = configTwitterwall;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
