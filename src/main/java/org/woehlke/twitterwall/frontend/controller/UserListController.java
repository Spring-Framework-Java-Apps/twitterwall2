package org.woehlke.twitterwall.frontend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.model.UserList;
import org.woehlke.twitterwall.oodm.service.UserListService;
import org.woehlke.twitterwall.oodm.service.UserService;

@Controller
@RequestMapping("/userlist")
public class UserListController {

    private final static String PATH="userlist";

    @RequestMapping("/all")
    public String getAll(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "slug"
        );
        Page<UserList> userlists = userListService.getAll(pageRequest);
        model.addAttribute("myPageContent", userlists);
        String symbol = Symbols.USER_ALL.toString();
        String subtitle = "All Users";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "userlist/all";
    }

    @RequestMapping("/{id}")
    public String getUserListForId(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        @PathVariable("id") UserList userList, Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            "createdAt"
        );
        String symbol = Symbols.USER_PROFILE.toString();
        String title = userList.getFullName();
        String subtitle = userList.getDescription();
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        Page<User> userPage = userService.findUsersForUserList(userList,pageRequest);
        model.addAttribute("users", userPage);
        model.addAttribute("userList", userList);
        return "userlist/id";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserListService userListService;

    private final UserService userService;

    private final FrontendProperties frontendProperties;

    private final ContentFactory contentFactory;

    private static String title = "User List";

    @Autowired
    public UserListController(UserListService userListService, UserService userService, FrontendProperties frontendProperties, ContentFactory contentFactory) {
        this.userListService = userListService;
        this.userService = userService;
        this.frontendProperties = frontendProperties;
        this.contentFactory = contentFactory;
    }
}
