package org.woehlke.twitterwall.scheduled.mq.msg.results;

import org.woehlke.twitterwall.oodm.entities.User;

import java.io.Serializable;
import java.util.List;

public class UserResultList  implements Serializable {

    private final long taskId;
    private final List<User> userList;

    public UserResultList(long taskId, List<User> userList) {
        this.taskId = taskId;
        this.userList = userList;
    }

    public long getTaskId() {
        return taskId;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResultList)) return false;

        UserResultList that = (UserResultList) o;

        if (getTaskId() != that.getTaskId()) return false;
        return getUserList() != null ? getUserList().equals(that.getUserList()) : that.getUserList() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getTaskId() ^ (getTaskId() >>> 32));
        result = 31 * result + (getUserList() != null ? getUserList().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserResultList{" +
            "taskId=" + taskId +
            ", userList=" + userList +
            '}';
    }
}
