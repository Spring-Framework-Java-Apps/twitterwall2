package org.woehlke.twitterwall.model;

import java.io.Serializable;

/**
 * Created by tw on 13.06.17.
 */
public class Page implements Serializable {

    private String title;
    private String subtitle;
    private String menuAppName;

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
}
