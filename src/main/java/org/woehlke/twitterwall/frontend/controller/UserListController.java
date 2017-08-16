package org.woehlke.twitterwall.frontend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.UserList;
import org.woehlke.twitterwall.oodm.service.UserListService;

@Controller
@RequestMapping("/userlist")
public class UserListController {

    private final static String PATH="userlist";

    @RequestMapping("/all")
    public String getAll(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        model.addAttribute("userlists", userListService.getAll(pageRequest));
        String symbol = Symbols.USER_ALL.toString();
        String subtitle = "All Users";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "userlist/all";
    }

    @RequestMapping("/{id}")
    public String getUserListForId(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        @PathVariable("id") UserList userList, Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            "createdAt"
        );
        String symbol = Symbols.PROFILE.toString();
        String title = userList.getFullName();
        String subtitle = userList.getDescription();
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        model.addAttribute("userList", userList);
        return "userlist/id";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserListService userListService;

    private final FrontendProperties frontendProperties;

    private final ControllerHelper controllerHelper;

    private static String title = "User List";

    @Autowired
    public UserListController(UserListService userListService, FrontendProperties frontendProperties, ControllerHelper controllerHelper) {
        this.userListService = userListService;
        this.frontendProperties = frontendProperties;
        this.controllerHelper = controllerHelper;
    }
}
