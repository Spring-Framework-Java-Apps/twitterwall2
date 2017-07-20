package org.woehlke.twitterwall;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 17.07.17.
 */
@Validated
@ConfigurationProperties(prefix="twitterwall")
public class ConfigTwitterwall {

    @Valid
    public Backend backenend = new Backend();

    @Valid
    public Frontend frontend = new Frontend();

    @Valid
    public Scheduler scheduler = new Scheduler();

    public static class Backend {

        @Valid
        public Twitter twitter = new Twitter();

        @Valid
        public Url url = new Url();

        public static class Twitter {

            @NotNull
            private Integer millisToWaitBetweenTwoApiCalls;

            @NotNull
            private Integer millisToWaitForFetchTweetsFromTwitterSearch;

            public Integer getMillisToWaitBetweenTwoApiCalls() {
                return millisToWaitBetweenTwoApiCalls;
            }

            public void setMillisToWaitBetweenTwoApiCalls(Integer millisToWaitBetweenTwoApiCalls) {
                this.millisToWaitBetweenTwoApiCalls = millisToWaitBetweenTwoApiCalls;
            }

            public Integer getMillisToWaitForFetchTweetsFromTwitterSearch() {
                return millisToWaitForFetchTweetsFromTwitterSearch;
            }

            public void setMillisToWaitForFetchTweetsFromTwitterSearch(Integer millisToWaitForFetchTweetsFromTwitterSearch) {
                this.millisToWaitForFetchTweetsFromTwitterSearch = millisToWaitForFetchTweetsFromTwitterSearch;
            }
        }

        public static class Url {

            @NotNull
            private Long connTimeToLive;

            @NotNull
            private Long maxIdleTime;

            @NotNull
            private Boolean fetchTestDataVerbose;

            public Long getConnTimeToLive() {
                return connTimeToLive;
            }

            public void setConnTimeToLive(Long connTimeToLive) {
                this.connTimeToLive = connTimeToLive;
            }

            public Long getMaxIdleTime() {
                return maxIdleTime;
            }

            public void setMaxIdleTime(Long maxIdleTime) {
                this.maxIdleTime = maxIdleTime;
            }

            public Boolean getFetchTestDataVerbose() {
                return fetchTestDataVerbose;
            }

            public void setFetchTestDataVerbose(Boolean fetchTestDataVerbose) {
                this.fetchTestDataVerbose = fetchTestDataVerbose;
            }

        }

        public Twitter getTwitter() {
            return twitter;
        }

        public void setTwitter(Twitter twitter) {
            this.twitter = twitter;
        }

        public Url getUrl() {
            return url;
        }

        public void setUrl(Url url) {
            this.url = url;
        }
    }

    public static class Frontend {

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
    }

    public static class Scheduler {

        @NotNull
        private Boolean allowFetchTweetsFromTwitterSearch;

        @NotNull
        private Boolean allowUpdateTweets;

        @NotNull
        private Boolean allowUpdateUserProfiles;

        @NotNull
        private Boolean allowUpdateUserProfilesFromMention;

        @NotNull
        private Integer herokuDbRowsLimit;

        @NotNull
        private Boolean skipFortesting;

        @Valid
        private FetchUserList fetchUserList = new FetchUserList();

        private Facade facade = new Facade();

        public Boolean getAllowFetchTweetsFromTwitterSearch() {
            return allowFetchTweetsFromTwitterSearch;
        }

        public void setAllowFetchTweetsFromTwitterSearch(Boolean allowFetchTweetsFromTwitterSearch) {
            this.allowFetchTweetsFromTwitterSearch = allowFetchTweetsFromTwitterSearch;
        }

        public Boolean getAllowUpdateTweets() {
            return allowUpdateTweets;
        }

        public void setAllowUpdateTweets(Boolean allowUpdateTweets) {
            this.allowUpdateTweets = allowUpdateTweets;
        }

        public Boolean getAllowUpdateUserProfiles() {
            return allowUpdateUserProfiles;
        }

        public void setAllowUpdateUserProfiles(Boolean allowUpdateUserProfiles) {
            this.allowUpdateUserProfiles = allowUpdateUserProfiles;
        }

        public Boolean getAllowUpdateUserProfilesFromMention() {
            return allowUpdateUserProfilesFromMention;
        }

        public void setAllowUpdateUserProfilesFromMention(Boolean allowUpdateUserProfilesFromMention) {
            this.allowUpdateUserProfilesFromMention = allowUpdateUserProfilesFromMention;
        }

        public Integer getHerokuDbRowsLimit() {
            return herokuDbRowsLimit;
        }

        public void setHerokuDbRowsLimit(Integer herokuDbRowsLimit) {
            this.herokuDbRowsLimit = herokuDbRowsLimit;
        }

        public Boolean getSkipFortesting() {
            return skipFortesting;
        }

        public void setSkipFortesting(Boolean skipFortesting) {
            this.skipFortesting = skipFortesting;
        }

        public static class FetchUserList {

            @NotNull
            private Boolean allow;

            @NotNull
            private String name;

            public Boolean getAllow() {
                return allow;
            }

            public void setAllow(Boolean allow) {
                this.allow = allow;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public FetchUserList getFetchUserList() {
            return fetchUserList;
        }

        public void setFetchUserList(FetchUserList fetchUserList) {
            this.fetchUserList = fetchUserList;
        }

        public static class Facade {

            private List<Long> idTwitterToFetchForTweetTest = new ArrayList<Long>();

            private List<Long> idTwitterToFetchForUserControllerTest = new ArrayList<Long>();

            public List<Long> getIdTwitterToFetchForTweetTest() {
                return idTwitterToFetchForTweetTest;
            }

            public void setIdTwitterToFetchForTweetTest(List<Long> idTwitterToFetchForTweetTest) {
                this.idTwitterToFetchForTweetTest = idTwitterToFetchForTweetTest;
            }

            public List<Long> getIdTwitterToFetchForUserControllerTest() {
                return idTwitterToFetchForUserControllerTest;
            }

            public void setIdTwitterToFetchForUserControllerTest(List<Long> idTwitterToFetchForUserControllerTest) {
                this.idTwitterToFetchForUserControllerTest = idTwitterToFetchForUserControllerTest;
            }
        }

        public Facade getFacade() {
            return facade;
        }

        public void setFacade(Facade facade) {
            this.facade = facade;
        }
    }

    public Backend getBackenend() {
        return backenend;
    }

    public void setBackenend(Backend backenend) {
        this.backenend = backenend;
    }

    public Frontend getFrontend() {
        return frontend;
    }

    public void setFrontend(Frontend frontend) {
        this.frontend = frontend;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}