package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.oodm.model.TaskHistory;
import org.woehlke.twitterwall.oodm.service.TaskHistoryService;

/**
 * Created by tw on 11.07.17.
 */
@Controller
@RequestMapping(path="/taskhistory")
public class TaskHistoryController {


    @RequestMapping(path="/all")
    public String getAll(
            @RequestParam(name= "page" ,defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
            Model model
    ){
        String subtitle = "all";
        String title = "TaskHistory";
        String symbol = Symbols.TASK_HISTORY.toString();
        model = contentFactory.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.DESC,
                "timeEvent"
        );
        Page<TaskHistory> myPageContent = taskHistoryService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "taskhistory/all";
    }

    private final TaskHistoryService taskHistoryService;

    private final FrontendProperties frontendProperties;

    private final ContentFactory contentFactory;

    @Autowired
    public TaskHistoryController(
            TaskHistoryService taskHistoryService,
            FrontendProperties frontendProperties,
            ContentFactory contentFactory
    ) {
        this.taskHistoryService = taskHistoryService;
        this.frontendProperties = frontendProperties;
        this.contentFactory = contentFactory;
    }

}
