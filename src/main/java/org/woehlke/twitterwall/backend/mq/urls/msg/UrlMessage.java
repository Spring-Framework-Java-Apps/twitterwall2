package org.woehlke.twitterwall.backend.mq.urls.msg;

import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;

import java.io.Serializable;

public class UrlMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final long urlId;
    private final String urlString;

    private final String expanded;
    private final String display;

    private final boolean ignoreNextSteps;

    public UrlMessage(TaskMessage taskMessage, long urlId, String urlString) {
        this.taskMessage = taskMessage;
        this.urlId = urlId;
        this.urlString = urlString;
        this.expanded = null;
        this.display = null;
        this.ignoreNextSteps = false;
    }

    public UrlMessage(TaskMessage taskMessage, long urlId, String urlString, String expanded, String display) {
        this.taskMessage = taskMessage;
        this.urlId = urlId;
        this.urlString = urlString;
        this.expanded = expanded;
        this.display = display;
        this.ignoreNextSteps = false;
    }

    public UrlMessage(TaskMessage taskMessage, long urlId, String urlString, boolean ignoreNextSteps) {
        this.taskMessage = taskMessage;
        this.urlId = urlId;
        this.urlString = urlString;
        this.expanded = null;
        this.display = null;
        this.ignoreNextSteps = ignoreNextSteps;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public long getUrlId() {
        return urlId;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getExpanded() {
        return expanded;
    }

    public String getDisplay() {
        return display;
    }

    public boolean isIgnoreNextSteps() {
        return ignoreNextSteps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMessage)) return false;

        UrlMessage that = (UrlMessage) o;

        if (urlId != that.urlId) return false;
        if (ignoreNextSteps != that.ignoreNextSteps) return false;
        if (taskMessage != null ? !taskMessage.equals(that.taskMessage) : that.taskMessage != null) return false;
        if (urlString != null ? !urlString.equals(that.urlString) : that.urlString != null) return false;
        if (expanded != null ? !expanded.equals(that.expanded) : that.expanded != null) return false;
        return display != null ? display.equals(that.display) : that.display == null;
    }

    @Override
    public int hashCode() {
        int result = taskMessage != null ? taskMessage.hashCode() : 0;
        result = 31 * result + (int) (urlId ^ (urlId >>> 32));
        result = 31 * result + (urlString != null ? urlString.hashCode() : 0);
        result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
        result = 31 * result + (display != null ? display.hashCode() : 0);
        result = 31 * result + (ignoreNextSteps ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlMessage{" +
                "taskMessage=" + taskMessage +
                ", urlId=" + urlId +
                ", urlString='" + urlString + '\'' +
                ", expanded='" + expanded + '\'' +
                ", display='" + display + '\'' +
                ", ignoreNextSteps=" + ignoreNextSteps +
                '}';
    }
}
