package org.woehlke.twitterwall.conf.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties(prefix="twitterwall.frontend")
public class FrontendProperties {

    @NotNull
    private String idGoogleAnalytics;

    @NotNull
    private String imprintScreenName;

    @NotNull
    private String imprintSubtitle;

    @NotNull
    private String infoWebpage;

    @NotNull
    private String menuAppName;

    @NotNull
    private String theme;

    @NotNull
    private Boolean contextTest;

    @NotNull
    private Integer pageSize;

    @NotNull
    private String loginUsername;

    @NotNull
    private String loginPassword;

    @Valid
    private Controller controller = new Controller();

    public static class Controller {

        @NotNull
        private Boolean fetchUsersFromDefinedUserList;

        public Boolean getFetchUsersFromDefinedUserList() {
            return fetchUsersFromDefinedUserList;
        }

        public void setFetchUsersFromDefinedUserList(Boolean fetchUsersFromDefinedUserList) {
            this.fetchUsersFromDefinedUserList = fetchUsersFromDefinedUserList;
        }
    }

    public String getIdGoogleAnalytics() {
        return idGoogleAnalytics;
    }

    public void setIdGoogleAnalytics(String idGoogleAnalytics) {
        this.idGoogleAnalytics = idGoogleAnalytics;
    }

    public String getImprintScreenName() {
        return imprintScreenName;
    }

    public void setImprintScreenName(String imprintScreenName) {
        this.imprintScreenName = imprintScreenName;
    }

    public String getImprintSubtitle() {
        return imprintSubtitle;
    }

    public void setImprintSubtitle(String imprintSubtitle) {
        this.imprintSubtitle = imprintSubtitle;
    }

    public String getInfoWebpage() {
        return infoWebpage;
    }

    public void setInfoWebpage(String infoWebpage) {
        this.infoWebpage = infoWebpage;
    }

    public String getMenuAppName() {
        return menuAppName;
    }

    public void setMenuAppName(String menuAppName) {
        this.menuAppName = menuAppName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getContextTest() {
        return contextTest;
    }

    public void setContextTest(Boolean contextTest) {
        this.contextTest = contextTest;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
