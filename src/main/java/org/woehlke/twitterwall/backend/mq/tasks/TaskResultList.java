package org.woehlke.twitterwall.backend.mq.tasks;

import org.woehlke.twitterwall.oodm.model.Task;

import java.io.Serializable;
import java.util.List;

public class TaskResultList implements Serializable{

    private final long taskId;
    private final List<Task> userList;

    public TaskResultList(long taskId, List<Task> userList) {
        this.taskId = taskId;
        this.userList = userList;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<Task> getUserList() {
        return userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskResultList)) return false;

        TaskResultList that = (TaskResultList) o;

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
        return "TaskResultList{" +
                "taskId=" + taskId +
                ", userList=" + userList +
                '}';
    }
}
