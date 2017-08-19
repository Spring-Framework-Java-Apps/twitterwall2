package org.woehlke.twitterwall.backend.mq.urls.msg;

import org.woehlke.twitterwall.oodm.model.Url;

import java.io.Serializable;
import java.util.List;

public class UrlResultList implements Serializable {

    private final long taskId;
    private final List<Long> urlIdList;

    public UrlResultList(long taskId, List<Long> urlIdList) {
        this.taskId = taskId;
        this.urlIdList = urlIdList;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<Long> getUrlIdList() {
        return urlIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlResultList)) return false;

        UrlResultList that = (UrlResultList) o;

        if (taskId != that.taskId) return false;
        return urlIdList != null ? urlIdList.equals(that.urlIdList) : that.urlIdList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (urlIdList != null ? urlIdList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlResultList{" +
                "taskId=" + taskId +
                ", urlIdList=" + urlIdList +
                '}';
    }
}
