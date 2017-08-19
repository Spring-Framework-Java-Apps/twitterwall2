package org.woehlke.twitterwall.backend.mq.urls.msg;

import org.woehlke.twitterwall.oodm.model.Url;

import java.io.Serializable;
import java.util.List;

public class UrlResultList implements Serializable {

    private final long taskId;
    private final List<Url> userList;

    public UrlResultList(long taskId, List<Url> userList) {
        this.taskId = taskId;
        this.userList = userList;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<Url> getUserList() {
        return userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlResultList)) return false;

        UrlResultList that = (UrlResultList) o;

        if (taskId != that.taskId) return false;
        return userList != null ? userList.equals(that.userList) : that.userList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (userList != null ? userList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlResultList{" +
                "taskId=" + taskId +
                ", userList=" + userList +
                '}';
    }
}
