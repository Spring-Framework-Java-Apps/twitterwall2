package org.woehlke.twitterwall.backend.mq.mentions.msg;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.backend.mq.tasks.TaskMessage;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.User;

import java.io.Serializable;

public class MentionMessage implements Serializable {

    private final TaskMessage taskMessage;
    private final String screenName;
    private final TwitterProfile twitterProfile;
    private final User user;

    private final long idTwitterOfUser;
    private final long idOfUser;
    private final long mentionId;

    private final boolean ignoreNextSteps;


    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName, boolean ignoreNextSteps) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = null;
        this.user = null;
        this.idTwitterOfUser = Mention.HAS_NO_USER;
        this.idOfUser = Mention.HAS_NO_USER;
        this.ignoreNextSteps = ignoreNextSteps;
    }

    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = null;
        this.user = null;
        this.idTwitterOfUser = Mention.HAS_NO_USER;
        this.idOfUser = Mention.HAS_NO_USER;
        this.ignoreNextSteps = false;
    }

    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName, TwitterProfile userFromTwitter) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = userFromTwitter;
        this.user = null;
        this.idTwitterOfUser = Mention.HAS_NO_USER;
        this.idOfUser = Mention.HAS_NO_USER;
        this.ignoreNextSteps = false;
    }

    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName, TwitterProfile twitterProfile, User user) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = twitterProfile;
        this.user = user;
        this.idTwitterOfUser = Mention.HAS_NO_USER;
        this.idOfUser = Mention.HAS_NO_USER;
        this.ignoreNextSteps = false;
    }

    public MentionMessage(TaskMessage taskMessage, long mentionId, String screenName, TwitterProfile twitterProfile, User user, long idOfUser, long idTwitterOfUser) {
        this.taskMessage = taskMessage;
        this.screenName = screenName;
        this.mentionId = mentionId;
        this.twitterProfile = twitterProfile;
        this.user = user;
        this.idTwitterOfUser = idTwitterOfUser;
        this.idOfUser = idOfUser;
        this.ignoreNextSteps = false;
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

    public User getUser() {
        return user;
    }

    public boolean isIgnoreNextSteps() {
        return ignoreNextSteps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MentionMessage)) return false;

        MentionMessage that = (MentionMessage) o;

        if (idTwitterOfUser != that.idTwitterOfUser) return false;
        if (idOfUser != that.idOfUser) return false;
        if (mentionId != that.mentionId) return false;
        if (ignoreNextSteps != that.ignoreNextSteps) return false;
        if (taskMessage != null ? !taskMessage.equals(that.taskMessage) : that.taskMessage != null) return false;
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) return false;
        if (twitterProfile != null ? !twitterProfile.equals(that.twitterProfile) : that.twitterProfile != null)
            return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = taskMessage != null ? taskMessage.hashCode() : 0;
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (twitterProfile != null ? twitterProfile.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (int) (idTwitterOfUser ^ (idTwitterOfUser >>> 32));
        result = 31 * result + (int) (idOfUser ^ (idOfUser >>> 32));
        result = 31 * result + (int) (mentionId ^ (mentionId >>> 32));
        result = 31 * result + (ignoreNextSteps ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MentionMessage{" +
                "taskMessage=" + taskMessage +
                ", screenName='" + screenName + '\'' +
                ", twitterProfile=" + twitterProfile +
                ", user=" + user +
                ", idTwitterOfUser=" + idTwitterOfUser +
                ", idOfUser=" + idOfUser +
                ", mentionId=" + mentionId +
                ", ignoreNextSteps=" + ignoreNextSteps +
                '}';
    }
}
