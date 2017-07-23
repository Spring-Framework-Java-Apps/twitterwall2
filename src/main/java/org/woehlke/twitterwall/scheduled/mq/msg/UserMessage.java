package org.woehlke.twitterwall.scheduled.mq.msg;

import org.woehlke.twitterwall.oodm.entities.User;

import java.io.Serializable;

public class UserMessage implements Serializable {

    private final long taskId;
    private final TwitterProfileMessage twitterProfileMessage;
    private final User user;

    public UserMessage(User user,long taskId){
        this.twitterProfileMessage = null;
        this.user = user;
        this.taskId = taskId;
    }

    public UserMessage(TwitterProfileMessage twitterProfileMessage, User user) {
        this.twitterProfileMessage = twitterProfileMessage;
        this.user = user;
        this.taskId = twitterProfileMessage.getTaskMessage().taskId;
    }

    public TwitterProfileMessage getTwitterProfileMessage() {
        return twitterProfileMessage;
    }

    public User getUser() {
        return user;
    }

    public long getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMessage)) return false;

        UserMessage that = (UserMessage) o;

        if (getTaskId() != that.getTaskId()) return false;
        if (getTwitterProfileMessage() != null ? !getTwitterProfileMessage().equals(that.getTwitterProfileMessage()) : that.getTwitterProfileMessage() != null)
            return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getTaskId() ^ (getTaskId() >>> 32));
        result = 31 * result + (getTwitterProfileMessage() != null ? getTwitterProfileMessage().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
            "taskId=" + taskId +
            ", twitterProfileMessage=" + twitterProfileMessage +
            ", user=" + user +
            '}';
    }
}