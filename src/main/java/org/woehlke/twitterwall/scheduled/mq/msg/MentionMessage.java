package org.woehlke.twitterwall.scheduled.mq.msg;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.Mention;

import java.io.Serializable;

public class MentionMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final String screenName;
    private final TwitterProfile twitterProfile;

    private final long idTwitterOfUser;
    private final long idOfUser;
    private final long mentionId;

    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = null;
        this.idTwitterOfUser = Mention.HAS_NO_USER;
        this.idOfUser = Mention.HAS_NO_USER;
    }

    public TaskMessage getTaskMessage() {
        return taskMessage;
    }

    public String getScreenName() {
        return screenName;
    }

    public TwitterProfile getTwitterProfile() {
        return twitterProfile;
    }

    public long getIdTwitterOfUser() {
        return idTwitterOfUser;
    }

    public long getIdOfUser() {
        return idOfUser;
    }

    public long getMentionId() {
        return mentionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MentionMessage)) return false;

        MentionMessage that = (MentionMessage) o;

        if (getIdTwitterOfUser() != that.getIdTwitterOfUser()) return false;
        if (getIdOfUser() != that.getIdOfUser()) return false;
        if (getMentionId() != that.getMentionId()) return false;
        if (getTaskMessage() != null ? !getTaskMessage().equals(that.getTaskMessage()) : that.getTaskMessage() != null)
            return false;
        if (getScreenName() != null ? !getScreenName().equals(that.getScreenName()) : that.getScreenName() != null)
            return false;
        return getTwitterProfile() != null ? getTwitterProfile().equals(that.getTwitterProfile()) : that.getTwitterProfile() == null;
    }

    @Override
    public int hashCode() {
        int result = getTaskMessage() != null ? getTaskMessage().hashCode() : 0;
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        result = 31 * result + (getTwitterProfile() != null ? getTwitterProfile().hashCode() : 0);
        result = 31 * result + (int) (getIdTwitterOfUser() ^ (getIdTwitterOfUser() >>> 32));
        result = 31 * result + (int) (getIdOfUser() ^ (getIdOfUser() >>> 32));
        result = 31 * result + (int) (getMentionId() ^ (getMentionId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MentionMessage{" +
            "taskMessage=" + taskMessage +
            ", screenName='" + screenName + '\'' +
            ", twitterProfile=" + twitterProfile +
            ", idTwitterOfUser=" + idTwitterOfUser +
            ", idOfUser=" + idOfUser +
            ", mentionId=" + mentionId +
            '}';
    }
}
