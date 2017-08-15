package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.social.twitter.api.UserList;

import java.io.Serializable;

public class UserListMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final org.springframework.social.twitter.api.UserList userListTwitter;
    private final org.woehlke.twitterwall.oodm.entities.UserList userList;
    private final long idTwitter;

    public UserListMessage(TaskMessage taskMessage, UserList userListTwitter, long idTwitter) {
        this.taskMessage = taskMessage;
        this.userListTwitter = userListTwitter;
        this.idTwitter = idTwitter;
        this.userList = null;
    }

    public UserListMessage(TaskMessage taskMessage, UserList userListTwitter, org.woehlke.twitterwall.oodm.entities.UserList userList, long idTwitter) {
        this.taskMessage = taskMessage;
        this.userListTwitter = userListTwitter;
        this.userList = userList;
        this.idTwitter = idTwitter;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public UserList getUserListTwitter() {
        return userListTwitter;
    }

    public org.woehlke.twitterwall.oodm.entities.UserList getUserList() {
        return userList;
    }

    public long getIdTwitter() {
        return idTwitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserListMessage)) return false;

        UserListMessage that = (UserListMessage) o;

        if (getIdTwitter() != that.getIdTwitter()) return false;
        if (getTaskMessage() != null ? !getTaskMessage().equals(that.getTaskMessage()) : that.getTaskMessage() != null)
            return false;
        if (getUserListTwitter() != null ? !getUserListTwitter().equals(that.getUserListTwitter()) : that.getUserListTwitter() != null)
            return false;
        return getUserList() != null ? getUserList().equals(that.getUserList()) : that.getUserList() == null;
    }

    @Override
    public int hashCode() {
        int result = getTaskMessage() != null ? getTaskMessage().hashCode() : 0;
        result = 31 * result + (getUserListTwitter() != null ? getUserListTwitter().hashCode() : 0);
        result = 31 * result + (getUserList() != null ? getUserList().hashCode() : 0);
        result = 31 * result + (int) (getIdTwitter() ^ (getIdTwitter() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserListMessage{" +
            "taskMessage=" + taskMessage +
            ", userListTwitter=" + userListTwitter +
            ", userList=" + userList +
            ", idTwitter=" + idTwitter +
            '}';
    }
}
