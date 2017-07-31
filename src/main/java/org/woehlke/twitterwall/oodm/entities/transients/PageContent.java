package org.woehlke.twitterwall.oodm.entities.transients;

import java.io.Serializable;

/**
 * Created by tw on 13.06.17.
 */
public class PageContent implements Serializable {

    private String title;
    private String subtitle;
    private String menuAppName;
    private String twitterSearchTerm;
    private String infoWebpage;
    private String symbol;
    private String theme;
    private String googleAnalyticScriptHtml;
    private boolean historyBack;
    private boolean contextTest;

    public String getGoogleAnalyticScriptHtml() {
        return googleAnalyticScriptHtml;
    }

    public void setGoogleAnalyticScriptHtml(String googleAnalyticScriptHtml) {
        this.googleAnalyticScriptHtml = googleAnalyticScriptHtml;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMenuAppName() {
        return menuAppName;
    }

    public void setMenuAppName(String menuAppName) {
        this.menuAppName = menuAppName;
    }

    public String getTwitterSearchTerm() {
        return twitterSearchTerm;
    }

    public void setTwitterSearchTerm(String twitterSearchTerm) {
        this.twitterSearchTerm = twitterSearchTerm;
    }

    public String getInfoWebpage() {
        return infoWebpage;
    }

    public void setInfoWebpage(String infoWebpage) {
        this.infoWebpage = infoWebpage;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isHistoryBack() {
        return historyBack;
    }

    public void setHistoryBack(boolean historyBack) {
        this.historyBack = historyBack;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isContextTest() {
        return contextTest;
    }

    public void setContextTest(boolean contextTest) {
        this.contextTest = contextTest;
    }

    @Override
    public String toString() {
        return "PageContent{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", menuAppName='" + menuAppName + '\'' +
                ", twitterSearchTerm='" + twitterSearchTerm + '\'' +
                ", infoWebpage='" + infoWebpage + '\'' +
                ", symbol='" + symbol + '\'' +
                ", theme='" + theme + '\'' +
                ", googleAnalyticScriptHtml='" + googleAnalyticScriptHtml + '\'' +
                ", historyBack=" + historyBack +
                ", contextTest=" + contextTest +
                '}';
    }
}
