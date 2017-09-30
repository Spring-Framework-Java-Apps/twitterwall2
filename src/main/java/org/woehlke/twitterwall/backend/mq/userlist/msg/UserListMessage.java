package org.woehlke.twitterwall.backend.mq.userlist.msg;

import org.springframework.social.twitter.api.UserList;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.oodm.model.parts.UserListType;

import java.io.Serializable;

public class UserListMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final org.springframework.social.twitter.api.UserList userListTwitter;
    private final org.woehlke.twitterwall.oodm.model.UserList userList;
    private final long idTwitterOfThisUserList;
    private final long idTwitterOfListOwningUser;
    private final UserListType userListType;


    public UserListMessage(TaskMessage taskMessage, UserList userListTwitter, long idTwitterOfThisUserList, long idTwitterOfListOwningUser, UserListType userListType) {
        this.taskMessage = taskMessage;
        this.userListTwitter = userListTwitter;
        this.idTwitterOfThisUserList = idTwitterOfThisUserList;
        this.userList = null;
        this.idTwitterOfListOwningUser = idTwitterOfListOwningUser;
        this.userListType = userListType;
    }

    public UserListMessage(TaskMessage taskMessage, UserList userListTwitter, org.woehlke.twitterwall.oodm.model.UserList userList, long idTwitterOfThisUserList, long idTwitterOfListOwningUser, UserListType userListType) {
        this.taskMessage = taskMessage;
        this.userListTwitter = userListTwitter;
        this.userList = userList;
        this.idTwitterOfThisUserList = idTwitterOfThisUserList;
        this.idTwitterOfListOwningUser = idTwitterOfListOwningUser;
        this.userListType = userListType;
    }

    public UserListMessage(TaskMessage taskMessage, UserList userListTwitter, long idTwitterOfListOwningUser, UserListType userListType) {
        this.taskMessage = taskMessage;
        this.userListTwitter = userListTwitter;
        this.userList = null;
        this.idTwitterOfThisUserList = userListTwitter.getId();
        this.idTwitterOfListOwningUser = idTwitterOfListOwningUser;
        this.userListType = userListType;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public UserList getUserListTwitter() {
        return userListTwitter;
    }

    public org.woehlke.twitterwall.oodm.model.UserList getUserList() {
        return userList;
    }

    public long getIdTwitterOfThisUserList() {
        return idTwitterOfThisUserList;
    }

    public long getIdTwitterOfListOwningUser() {
        return idTwitterOfListOwningUser;
    }

    public UserListType getUserListType() {
        return userListType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserListMessage)) return false;

        UserListMessage that = (UserListMessage) o;

        if (idTwitterOfThisUserList != that.idTwitterOfThisUserList) return false;
        if (idTwitterOfListOwningUser != that.idTwitterOfListOwningUser) return false;
        if (taskMessage != null ? !taskMessage.equals(that.taskMessage) : that.taskMessage != null) return false;
        if (userListTwitter != null ? !userListTwitter.equals(that.userListTwitter) : that.userListTwitter != null)
            return false;
        if (userList != null ? !userList.equals(that.userList) : that.userList != null) return false;
        return userListType == that.userListType;
    }

    @Override
    public int hashCode() {
        int result = taskMessage != null ? taskMessage.hashCode() : 0;
        result = 31 * result + (userListTwitter != null ? userListTwitter.hashCode() : 0);
        result = 31 * result + (userList != null ? userList.hashCode() : 0);
        result = 31 * result + (int) (idTwitterOfThisUserList ^ (idTwitterOfThisUserList >>> 32));
        result = 31 * result + (int) (idTwitterOfListOwningUser ^ (idTwitterOfListOwningUser >>> 32));
        result = 31 * result + (userListType != null ? userListType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserListMessage{" +
                "taskMessage=" + taskMessage +
                ", userListTwitter=" + userListTwitter +
                ", userList=" + userList +
                ", idTwitterOfThisUserList=" + idTwitterOfThisUserList +
                ", idTwitterOfListOwningUser=" + idTwitterOfListOwningUser +
                ", userListType=" + userListType +
                '}';
    }
}
