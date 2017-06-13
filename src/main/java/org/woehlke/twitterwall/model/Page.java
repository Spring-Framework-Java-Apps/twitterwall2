package org.woehlke.twitterwall.model;

import java.io.Serializable;

/**
 * Created by tw on 13.06.17.
 */
public class Page implements Serializable {

    private String title;
    private String subtitle;

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
}
