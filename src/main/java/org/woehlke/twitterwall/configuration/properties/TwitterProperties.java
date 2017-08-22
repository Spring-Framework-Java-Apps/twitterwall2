package org.woehlke.twitterwall.configuration.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties(prefix="twitter")
public class TwitterProperties {

    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer millisToWaitBetweenTwoApiCalls;

    @NotNull
    private String searchQuery;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Integer getMillisToWaitBetweenTwoApiCalls() {
        return millisToWaitBetweenTwoApiCalls;
    }

    public void setMillisToWaitBetweenTwoApiCalls(Integer millisToWaitBetweenTwoApiCalls) {
        this.millisToWaitBetweenTwoApiCalls = millisToWaitBetweenTwoApiCalls;
    }
}
