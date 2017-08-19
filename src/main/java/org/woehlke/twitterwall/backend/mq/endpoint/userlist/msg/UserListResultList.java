package org.woehlke.twitterwall.backend.mq.endpoint.userlist.msg;

import org.woehlke.twitterwall.oodm.model.UserList;

import java.util.List;

public class UserListResultList {

    private final long taskId;
    private final List<UserList> resultList;

    public UserListResultList(long taskId, List<UserList> resultList) {
        this.resultList=resultList;
        this.taskId=taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<UserList> getResultList() {
        return resultList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserListResultList)) return false;

        UserListResultList that = (UserListResultList) o;

        if (taskId != that.taskId) return false;
        return resultList != null ? resultList.equals(that.resultList) : that.resultList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (resultList != null ? resultList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserListResultList{" +
                "taskId=" + taskId +
                ", resultList=" + resultList +
                '}';
    }
}
