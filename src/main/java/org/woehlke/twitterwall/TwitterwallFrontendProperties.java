package org.woehlke.twitterwall;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="twitterwall.frontend")
public class TwitterwallFrontendProperties {

    private String idGoogleAnalytics;

    private String imprintScreenName;

    private String imprintSubtitle;

    private String infoWebpage;

    private String menuAppName;

    private String theme;

    private Boolean contextTest;

    private Integer pageSize;

    private Controller controller = new Controller();

    public static class Controller {

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
}
